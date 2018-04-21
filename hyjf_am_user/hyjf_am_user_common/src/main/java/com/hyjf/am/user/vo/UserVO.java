package com.hyjf.am.user.vo;

/**
 * @author xiasq
 * @version UserVO, v0.1 2018/1/21 22:39
 */
public class UserVO extends BaseVO {

	private Integer userId;

	private String username;

	private String mobile;

	private String email;

	private String password;

	private String referrerUserName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReferrerUserName() {
		return referrerUserName;
	}

	public void setReferrerUserName(String referrerUserName) {
		this.referrerUserName = referrerUserName;
	}
}
