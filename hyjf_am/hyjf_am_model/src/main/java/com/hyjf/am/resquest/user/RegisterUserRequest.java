package com.hyjf.am.resquest.user;

/**
 * @author xiasq
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class RegisterUserRequest {
	private String mobilephone;
	private String smsCode;
	private String reffer;
	private String password;
	private String utmId;
	private String loginIp;
	private String platform;

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
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

	public String getUtmId() {
		return utmId;
	}

	public void setUtmId(String utmId) {
		this.utmId = utmId;
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
