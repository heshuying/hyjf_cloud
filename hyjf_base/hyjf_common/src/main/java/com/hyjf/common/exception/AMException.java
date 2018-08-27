package com.hyjf.common.exception;

/**
 * 原子层返回ERROR时，自定义异常
 * @author liubin
 * @version AMException, v0.1 2018/4/12 11:09
 */
public class AMException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AMException() {
		super();
	}

	public AMException(String message) {
		super(message);
	}

	public AMException(Throwable cause) {
		super(cause);
	}

	public AMException(String message, Throwable cause) {
		super(message, cause);
	}
}
