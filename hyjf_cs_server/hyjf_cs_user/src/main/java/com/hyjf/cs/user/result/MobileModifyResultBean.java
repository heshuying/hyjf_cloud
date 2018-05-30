package com.hyjf.cs.user.result;

public class MobileModifyResultBean extends ApiResult<Object>{
	// 手机号
	String mobile;
	// 脱敏手机号
	String hideMobile;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHideMobile() {
		return hideMobile;
	}
	public void setHideMobile(String hideMobile) {
		this.hideMobile = hideMobile;
	}
	
}
