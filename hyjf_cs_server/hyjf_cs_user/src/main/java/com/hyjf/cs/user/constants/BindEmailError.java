package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.MsgCode;

public enum BindEmailError implements MsgCode {
		REQUEST_PARAM_ERROR("1", "请求参数错误"),
	 	EMAIL_EMPTY_ERROR("1", "待绑定的邮箱不能为空"),
	 	EMAIL_USED_ERROR("1", "邮箱已被占用"),
		EMAIL_FORMAT_ERROR("1", "邮箱格式不正确"),
		EMAIL_ACTIVE_SEND_ERROR("1", "发送激活邮件失败"),
		
	    EMAIL_ACTIVE_ERROR_1("1", "激活邮件未验证"),
	    EMAIL_ACTIVE_ERROR_2("1", "激活邮件已验证"),
	    EMAIL_ACTIVE_ERROR_3("1", "激活邮件已过期"),
	    EMAIL_ACTIVE_ERROR_4("1", "激活邮件不存在"),
	
	    EMAIL_ACTIVE_ERROR("1", "激活失败");

		private String code;
		private String msg;

		BindEmailError(String code, String msg) {
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
