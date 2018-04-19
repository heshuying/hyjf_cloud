package com.hyjf.common.exception;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author xiasq
 * @version ReturnMessageException, v0.1 2018/1/21 22:22
 */
public class ReturnMessageException extends RuntimeException {

	private ErrorCode error;

	public ReturnMessageException(ErrorCode error) {
		this.error = error;
	}

	public ReturnMessageException(ErrorCode error, Throwable e) {
		super(e);
		this.error = error;
	}

	public ReturnMessageException() {
		super();
	}

	public ReturnMessageException(String msg, Throwable e) {
		super(msg, e);
	}

	public ReturnMessageException(String msg) {
		super(msg);
	}

	public ReturnMessageException(Throwable e) {
		super(e);
	}

	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.error = error;
	}

}
