package com.hyjf.common.exception;

/**
 * @author xiasq
 * @version MQException, v0.1 2018/4/12 11:09
 */
public class MQException extends Exception {
	/**
	 * MQ 异常
	 */
	private static final long serialVersionUID = 1L;

	public MQException() {
		super();
	}

	public MQException(String message, Throwable cause) {
		super(message, cause);
	}
}
