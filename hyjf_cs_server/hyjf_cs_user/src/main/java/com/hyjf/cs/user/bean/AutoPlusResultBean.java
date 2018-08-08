package com.hyjf.cs.user.bean;

import com.hyjf.cs.user.result.BaseResultBean;

/**
 * @author xiasq
 * @version AutoPlusResultBean, v0.1 2017/12/23 10:17
 */
public class AutoPlusResultBean extends BaseResultBean {
	public AutoPlusResultBean(String request) {
		super(request);
	}

	// 前导业务码
	private String srvAuthCode;

	// 授权状态 0未授权 1已授权
	private int userAutoStatus;

	// 跳转的url
	private String authUrl;

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getSrvAuthCode() {
		return srvAuthCode;
	}

	public void setSrvAuthCode(String srvAuthCode) {
		this.srvAuthCode = srvAuthCode;
	}

	public int getUserAutoStatus() {
		return userAutoStatus;
	}

	public void setUserAutoStatus(int userAutoStatus) {
		this.userAutoStatus = userAutoStatus;
	}
}
