package com.hyjf.admin.constant;

import com.hyjf.common.constants.MsgCode;

/**
 * @author xiasq
 * @version ApiError, v0.1 2018/5/24 16:47
 */
public enum ApiError implements MsgCode {
    //未知错误
    UNKNOWN_ERROR("syserror_0001", "系统异常，请稍后再试"),
    API_CALL_ERROR("system_error_0002", "微服务调用异常，请稍后重试！");


	private String code;
	private String msg;

	ApiError(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}
}
