package com.hyjf.am.vo.user;

import java.io.Serializable;

public class UserAliasVO implements Serializable {
	private Integer userId;

	private String sign;

	/**
	 * 设备唯一标识码（极光别名）
	 */
	private String alias;
	/**
	 * 手机号
	 *
	 * @return
	 */
	private String mobile;
	/**
	 * 手机号
	 *
	 * @return
	 */
	private String[] mobiles;
	/**
	 * 包号，39 新极光 79老极光 推送
	 */
	private String packageCode;

	/**
	 * 所属平台（2 安卓 3 ios）
	 */
	private String client;

	private static final long serialVersionUID = 1L;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String[] getMobiles() {
		return mobiles;
	}

	public void setMobiles(String[] mobiles) {
		this.mobiles = mobiles;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
}