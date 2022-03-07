package top.kudaompq.annotation;

import top.kudaompq.enums.VisitBehavior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 用于记录访客访问日志的方法
* @Author: Kudaompq
* @Date: 2022/1/30
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {

    VisitBehavior value() default VisitBehavior.UNKNOWN;
}
