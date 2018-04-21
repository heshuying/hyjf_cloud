package com.hyjf.am.user.request;

/**
 * @author xiasq
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class RegisterUserRequest {
	private String mobilephone;
	private String smscode;
	private String reffer;
	private String password;
	private String utm_id;
	private String loginIp;
	private String platform;

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public String getReffer() {
		return reffer;
	}

	public void setReffer(String reffer) {
		this.reffer = reffer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUtm_id() {
		return utm_id;
	}

	public void setUtm_id(String utm_id) {
		this.utm_id = utm_id;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
