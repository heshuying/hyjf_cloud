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
	PARAM_ERROR("1", ""),
	GET_USER_INFO_ERROR("1", "获取用户信息失败"),
	MOBILE_NULL_ERROR("1", "手机号不能为空"),
	TRUENAME_NULL_ERROR("1", "真实姓名不能为空"),

	TRUENAME_BLANKL_ERROR("1", "真实姓名不能包含空格"),
	TRUENAME_LENGTH_ERROR("1", "真实姓名不能超过十位"),
	IDNO_NULL_ERROR("1", "身份证不能为空"),
	IDNO_FORMAT_ERROR("1", "身份证号格式错误"),
	IDNO_USED_ERROR("1", "身份证号已存在"),
	MOBILE_FORMAT_ERROR("1", "手机号格式错误"),
	MOBILE_USED_ERROR("1", "手机号码重复"),
	MOBILE_ERROR("1", "手机号码输入错误"),

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
