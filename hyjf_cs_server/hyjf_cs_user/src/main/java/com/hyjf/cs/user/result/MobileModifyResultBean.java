package com.hyjf.cs.user.result;

import com.hyjf.cs.common.bean.result.WebResult;

/**
 * 手机号修改返回bean
 * @author hesy
 *
 */
public class MobileModifyResultBean extends WebResult<Object>{
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
