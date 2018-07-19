package com.hyjf.am.vo.user;

import java.io.Serializable;

public class UserAliasVO implements Serializable {
	private Integer userId;

	private String sign;

	private String alias;

	private String client;

	private String packageCode;

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
		this.sign = sign == null ? null : sign.trim();
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias == null ? null : alias.trim();
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client == null ? null : client.trim();
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode == null ? null : packageCode.trim();
	}
}