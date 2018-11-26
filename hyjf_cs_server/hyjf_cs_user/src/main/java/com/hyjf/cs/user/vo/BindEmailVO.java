package com.hyjf.cs.user.vo;

/**
 * 绑定邮箱vo
 * @author hesy
 */
public class BindEmailVO {
	
	private String key;
	private String value;
	private String email;
	private String isUpdate;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
}

	