package com.hyjf.common.exception;

import com.hyjf.common.constants.MsgCode;

/**
 * @author xiasq
 * @version ServiceException, v0.1 2018/1/21 22:20
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private MsgCode errorCode;

	public ServiceException() {
	}

	public ServiceException(MsgCode errorCode) {
		this.errorCode = errorCode;
	}
	public ServiceException(MsgCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public MsgCode getErrorCode() {
		return errorCode;
	}

	public boolean hasErrorCode() {
		return errorCode != null;
	}

	public void setErrorCode(MsgCode errorCode) {
		this.errorCode = errorCode;
	}

}