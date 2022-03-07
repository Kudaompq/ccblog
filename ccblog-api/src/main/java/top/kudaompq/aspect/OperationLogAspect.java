package top.kudaompq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.entity.OperationLog;
import top.kudaompq.entity.User;
import top.kudaompq.service.OperationLogService;
import top.kudaompq.service.UserService;
import top.kudaompq.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: AOP记录操作日志
 * @author: kudaompq
 * @date: 2022/1/30 11:53
 * @version: v1.0
 */

@Component
@Aspect
public class OperationLogAspect {

    @Autowired
    OperationLogService operationLogService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(operationLogger)")
    public void logPointcut(OperationLogger operationLogger){}


    /**
     * 配置环绕通知
     */
    @Around("logPointcut(operationLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint,OperationLogger operationLogger) throws Throwable{
        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        int times = (int) (System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        OperationLog operationLog = handleLog(joinPoint, operationLogger, times);
        operationLogService.saveOperationLog(operationLog);
        return result;

    }

    /**
    * @Description: 获取HttpServletRequest请求对象，并设置OperationLog对象属性
    * @Param: [joinPoint, operationLogger, times]
    * @return: top.kudaompq.entity.OperationLog
    * @Author: Kudaompq
    * @Date: 2022/1/30
    */
    private OperationLog handleLog(ProceedingJoinPoint joinPoint,OperationLogger operationLogger,int times){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userId = jwtUtils.getClaimByToken(request.getHeader("Authorization")).getSubject();
        User user = userService.getById(new Long(userId));
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String description = operationLogger.value();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        OperationLog log = new OperationLog(user.getUsername(), uri, method, description, ip, times, userAgent);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams),0,2000));
        return log;
    }
}
