package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author sunss
 * @version RegisterError, v0.1
 */
public enum OpenAccountError implements ErrorCode {
	SUCCESS("0", ""),
	USER_NOT_LOGIN_ERROR("1", "用户未登录"),
	ERROR("1", "开户失败"),
	SYSTEM_ERROR("1","系统异常"),
	PARAM_ERROR("1", "")
	;

	private String errCode;
	private String message;

	OpenAccountError(String errCode, String message) {
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
