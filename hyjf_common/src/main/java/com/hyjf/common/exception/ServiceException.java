package com.hyjf.common.exception;

/**
 * @author xiasq
 * @version ServiceException, v0.1 2018/1/21 22:20
 */
public class ServiceException extends Exception {
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
