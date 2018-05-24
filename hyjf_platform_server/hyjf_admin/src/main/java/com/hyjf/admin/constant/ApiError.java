package com.hyjf.admin.constant;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author xiasq
 * @version ApiError, v0.1 2018/5/24 16:47
 */
public enum ApiError implements ErrorCode{
    //未知错误
    UNKNOWN_ERROR("syserror_0001", "系统异常，请稍后再试"),
    API_CALL_ERROR("system_error_0002", "微服务调用异常，请稍后重试！");


	private String errCode;
	private String message;

	ApiError(String errCode, String message) {
		this.errCode = errCode;
		this.message = message;
	}

	@Override
	public String getErrCode() {
		return this.errCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
