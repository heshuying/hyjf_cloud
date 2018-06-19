package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.MsgCode;

public enum ContractSetError implements MsgCode {
	
	REQUEST_PARAM_ERROR("1", "请求参数错误"),
	NAME_FORMAT_ERROR("1", "联系人姓名格式错误"),
	PHONE_FORMAT_ERROR("1", "联系人手机号码格式错误"),
	RELATION_NOTEXIST_ERROR("1", "无效的紧急联系人关系"),
	CONTRACT_SAVE_ERROR("1", "紧急联系人保存错误"),
	CONTRACT_RELATION_ERROR("1", "紧急联系人关系数据不存在错误");
	
	private String code;
	private String msg;

	ContractSetError(String code, String msg) {
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
