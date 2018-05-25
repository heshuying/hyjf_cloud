package com.hyjf.batch.frame.exception;

/**
 * @author xiasq
 * @version TaskManageController, v0.1 2018/1/20 22:15
 */
public class ApiCallException extends Exception {

	/**
	 * 接口调用异常
	 */
	private static final long serialVersionUID = 1L;

	public ApiCallException() {
		super();
	}

	public ApiCallException(String message, Throwable cause) {
		super(message, cause);
	}
}
