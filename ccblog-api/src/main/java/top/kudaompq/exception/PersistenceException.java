package top.kudaompq.exception;

/**
 * @Description: 持久化异常
 * @Author: Kudaompq
 * @Date: 2022-1-30
 */

public class PersistenceException extends RuntimeException {
	public PersistenceException() {
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
}
