package top.kudaompq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.RedisKey;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Blog;
import top.kudaompq.entity.VisitLog;
import top.kudaompq.entity.Visitor;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.dto.VisitLogRemark;
import top.kudaompq.service.RedisService;
import top.kudaompq.service.VisitLogService;
import top.kudaompq.service.VisitorService;
import top.kudaompq.utils.AopUtils;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.JacksonUtils;
import top.kudaompq.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/7 12:33
 * @version: v1.0
 */

@Component
@Aspect
public class VisitLogAspect {
    @Autowired
    RedisService redisService;

    @Autowired
    VisitorService visitorService;

    @Autowired
    VisitLogService visitLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 切入
     * @param visitLogger
     */
    @Pointcut("@annotation(visitLogger)")
    public void logPointcut(VisitLogger visitLogger){
    }

    /**
     * 环绕切入点
     * @param joinPoint
     * @param visitLogger
     * @return
     * @throws Throwable
     */
    @Around("logPointcut(visitLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint,VisitLogger visitLogger) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        Result result = (Result) joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //校验访客标识码
        String identification = checkIdentification(request);
        // 记录访问日志
        VisitLog visitLog = handleLog(joinPoint,visitLogger,request,result,times,identification);
        visitLogService.saveVisitLog(visitLog);
        return result;
    }


    /**
    * @Description: 校验访客标识符
    * @Param: [request]
    * @return: java.lang.String
    * @Author: Kudaompq
    * @Date: 2022/2/7
    */
    private String checkIdentification(HttpServletRequest request){
        String identification = request.getHeader("identification");
        if (identification == null){
            identification = saveUUID(request);
        }else{
            boolean redisHas = redisService.hasValueInSet(RedisKey.IDENTIFICATION_SET, identification);
            if (!redisHas){
                Boolean mysqlHas = visitorService.hasUUID(identification);
                if (mysqlHas){
                    // 数据库存在，保存至redis
                    redisService.saveValueToSet(RedisKey.IDENTIFICATION_SET,identification);
                }else{
                    // 数据库中不存在，签发新的UUID
                    identification = saveUUID(request);
                }
            }
        }
        return identification;
    }

    /**
    * @Description: 签发UUID
    * @Param: [request]
    * @return: java.lang.String
    * @Author: Kudaompq
    * @Date: 2022/2/7
    */
    private String saveUUID(HttpServletRequest request){
        //获取响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Calendar calendar = Calendar.getInstance();
        // 获得当前时间戳，精确到小时，防止刷访客记录
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        // 获得访客基本信息
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        //根据时间戳、ip、userAgent生成UUID
        String nameUUID = timestamp + ip + userAgent;
        String uuid = UUID.nameUUIDFromBytes(nameUUID.getBytes()).toString();
        // 将访客标识符UUID放到响应头
        response.setHeader("identification",uuid);
        //暴露自定义header供页面资源使用
        response.addHeader("Access-Control-Expose-Headers", "identification");
        boolean redisHas = redisService.hasValueInSet(RedisKey.IDENTIFICATION_SET, uuid);
        if (!redisHas){
            // 保存到redis
            redisService.saveValueToSet(RedisKey.IDENTIFICATION_SET,uuid);
            Visitor visitor = new Visitor(uuid, ip, userAgent);
            visitorService.saveVisitor(visitor);
        }
        return uuid;
    }

    /**
    * @Description: 拦截日志
    * @Param: [joinPoint, visitLogger, request, result, times, identification]
    * @return: top.kudaompq.entity.VisitLog
    * @Author: Kudaompq
    * @Date: 2022/2/7
    */
    private VisitLog handleLog(ProceedingJoinPoint joinPoint,
                               VisitLogger visitLogger,
                               HttpServletRequest request,
                               Result result,
                               int times,
                               String identification){
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        VisitLogRemark visitLogRemark = judgeBehavior(visitLogger.value(), requestParams, result);
        VisitLog visitLog = new VisitLog(identification,uri,method,visitLogger.value().getBehavior(),
                visitLogRemark.getContent(),visitLogRemark.getRemark(),ip,times,userAgent);
        visitLog.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams),0,2000));
        return visitLog;

    }

    /**
    * @Description: 根据访问行为，设置对应的访问内容或者备注
    * @Param: [behavior, requestParams, result]
    * @return: top.kudaompq.model.dto.VisitLogRemark
    * @Author: Kudaompq
    * @Date: 2022/2/7
    */
    private VisitLogRemark judgeBehavior(VisitBehavior behavior,Map<String,Object> requestParams,Result result){
        String remark = "";
        String content = behavior.getContent();
        switch (behavior) {
            case INDEX:
            case MOMENT:
                remark = "第" + requestParams.get("pageNum") + "页";
                break;
            case BLOG:
                if (result.getCode() == 200) {
                    BlogDetail blog = (BlogDetail) result.getData();
                    String title = blog.getTitle();
                    content = title;
                    remark = "文章标题：" + title;
                }
                break;
            case SEARCH:
                if (result.getCode() == 200) {
                    String query = (String) requestParams.get("query");
                    content = query;
                    remark = "搜索内容：" + query;
                }
                break;
            case CATEGORY:
                String categoryName = (String) requestParams.get("categoryName");
                content = categoryName;
                remark = "分类名称：" + categoryName + "，第" + requestParams.get("pageNum") + "页";
                break;
            case TAG:
                String tagName = (String) requestParams.get("tagName");
                content = tagName;
                remark = "标签名称：" + tagName + "，第" + requestParams.get("pageNum") + "页";
                break;
            case CLICK_FRIEND:
                String nickname = (String) requestParams.get("nickname");
                content = nickname;
                remark = "友链名称：" + nickname;
                break;
        }
        return new VisitLogRemark(content,remark);
    }
}
