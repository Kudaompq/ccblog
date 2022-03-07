package top.kudaompq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 用于需要记录操作的方法
* @Author: Kudaompq
* @Date: 2022/1/30
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogger {

    /**
    *  操作描述
    */
    String value() default "";
}
