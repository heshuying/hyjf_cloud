package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author xiasq
 * @version RegisterError, v0.1 2018/4/11 9:43
 */
public enum RegisterError implements ErrorCode {
	REGISTER_ERROR("1", "注册失败"),
	MOBILE_IS_NOT_NULL_ERROR("1", "请填写手机号"),
	SMSCODE_IS_NOT_NULL_ERROR("1", "验证码不能为空"),
	PASSWORD_IS_NOT_NULL_ERROR("1", "密码不能为空"),
	MOBILE_IS_NOT_REAL_ERROR("1", "请填写您的真实手机号码"),
	MOBILE_EXISTS_ERROR("1", "手机号已存在"),
	PASSWORD_LENGTH_ERROR("1", "密码长度6-16位"),
	PASSWORD_NO_NUMBER_ERROR("1", "密码必须包含数字"),
	PASSWORD_FORMAT_ERROR("1", "密码必须由数字和字母组成，如abc123"),
	SMSCODE_INVALID_ERROR("1", "验证码无效"),
	REFFER_INVALID_ERROR("1", "推荐人无效"),
	MOBILE_NEED_SAME_ERROR("1", "获取验证码手机号与注册手机号不一致!"),
	MOBILE_MODIFY_ERROR("1", "修改手机号与原手机号不能相同!"),
	SEND_SMSCODE_TOO_FAST_ERROR("1", "请求验证码操作过快!"),


	CODETYPE_INVALID_ERROR("1", "无效的验证码类型!"),
	MOBILE_FORMAT_ERROR("1", "手机号格式不正确"),
	USER_NOT_EXISTS_ERROR("1", "不存在用户")


	;

	private String errCode;
	private String message;

	RegisterError(String errCode, String message) {
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
