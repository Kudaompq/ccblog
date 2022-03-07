package top.kudaompq.exception;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/11 17:41
 * @version: v1.0
 */

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
