package top.kudaompq.exception;

/**
 * @description: 非法请求异常
 * @author: kudaompq
 * @date: 2022/2/10 13:20
 * @version: v1.0
 */

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
