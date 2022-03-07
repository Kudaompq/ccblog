package top.kudaompq.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.entity.ExceptionLog;
import top.kudaompq.service.ExceptionLogService;
import top.kudaompq.utils.AopUtils;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.JacksonUtils;
import top.kudaompq.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: AOP记录异常日志
 * @author: kudaompq
 * @date: 2022/2/12 14:09
 * @version: v1.0
 */

@Component
@Aspect
public class ExceptionLogAspect {

    @Autowired
    ExceptionLogService exceptionLogService;

    /**
     * 配置切入点
     */
    @Pointcut("execution(* top.kudaompq.controller..*..*(..))")
    public void logPointCut(){}

    @AfterThrowing(value = "logPointCut()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint,Exception e){
        ExceptionLog exceptionLog = handleLog(joinPoint, e);
        exceptionLogService.saveExceptionLog(exceptionLog);
    }

    private ExceptionLog handleLog(JoinPoint joinPoint,Exception e){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        // 直接使用注解上得内容作为description
        String description = getDescriptionFromAnnotations(joinPoint);
        String error = StringUtils.getStackTrace(e);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        String param = StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000);
        ExceptionLog exceptionLog = new ExceptionLog(uri, method, param, description, error, ip, userAgent);
        return exceptionLog;
    }

    private String getDescriptionFromAnnotations(JoinPoint joinPoint){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
        if (operationLogger != null){
            return operationLogger.value();
        }
        VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        if (visitLogger != null){
            return visitLogger.value().getBehavior();
        }
        return "";
    }
}
