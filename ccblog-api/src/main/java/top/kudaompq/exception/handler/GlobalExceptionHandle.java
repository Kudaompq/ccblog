package top.kudaompq.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.kudaompq.common.Result;
import top.kudaompq.common.ResultCode;
import top.kudaompq.exception.BadRequestException;
import top.kudaompq.exception.PersistenceException;

/**
 * @description: 全局异常捕获类
 * @author: kudaompq
 * @date: 2022/1/2 15:34
 * @version: v1.0
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
    * @Description: 捕获全部异常
    * @Param: [e]
    * @return: top.kudaompq.vueblog.common.lang.Result
    * @Author: Kudaompq
    * @Date: 2022/1/2
    */
    @ExceptionHandler(value = RuntimeException.class)
    public Result handle(RuntimeException e){

        log.error("运行时异常:--------------{}",e);
        return Result.error(e.getMessage());
    }

    /**
    * @Description: 捕获shiro鉴权错误
    * @Param: [e]
    * @return: top.kudaompq.vueblog.common.lang.Result
    * @Author: Kudaompq
    * @Date: 2022/1/2
    */
    @ExceptionHandler(value = ShiroException.class)
    public Result handle(ShiroException e){
        log.error("shiro权鉴异常:--------------{}",e);
        return Result.error(ResultCode.UN_AUTHORIZED,e.getMessage(),null);
    }

    /** 
    * @Description: 捕获权限不足时的异常 
    * @Param: [e] 
    * @return: top.kudaompq.common.Result 
    * @Author: Kudaompq
    * @Date: 2022/1/4 
    */
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result handle(UnauthorizedException e){
        log.error("权限异常:--------------{}",e);
        return Result.error(ResultCode.FORBIDDEN,"权限不足");
    }


    /** 
    * @Description: 捕获实体校验异常 
    * @Param: [e] 
    * @return: top.kudaompq.vueblog.common.lang.Result 
    * @Author: Kudaompq
    * @Date: 2022/1/2 
    */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handle(MethodArgumentNotValidException e){
        log.error("实体校验异常:--------------{}",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.error(objectError.getDefaultMessage());
    }

    /** 
    * @Description: 捕获Assert异常
    * @Param: [e] 
    * @return: top.kudaompq.vueblog.common.lang.Result 
    * @Author: Kudaompq
    * @Date: 2022/1/2 
    */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handle(IllegalArgumentException e){
        log.error("Assert异常:--------------{}",e);
        return Result.error(e.getMessage());
    }
    
    
    /** 
    * @Description: 捕获数据库持久化错误
    * @Param: [e] 
    * @return: top.kudaompq.common.Result 
    * @Author: Kudaompq
    * @Date: 2022/1/30 
    */
    @ExceptionHandler(value = PersistenceException.class)
    public Result handle(PersistenceException e){
        log.error("持久化异常:---------------{}",e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = ExpiredCredentialsException.class)
    public Result handle(ExpiredCredentialsException e){
        log.error("用户token失效");
        return Result.error("用户token已失效，请重新登录");
    }

}

