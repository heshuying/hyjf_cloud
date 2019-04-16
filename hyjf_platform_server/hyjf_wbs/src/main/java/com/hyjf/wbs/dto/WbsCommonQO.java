/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用请求参数封装
 * 
 * @author cui
 * @version WbsCommonQO, v0.1 2019/4/16 14:31
 */
public class WbsCommonQO {

	private String name;
	private String app_key;
	private String data;

	// "yyyy-MM-dd HH:mm:ss"
	private String timestamp;
	private String version;
	private String access_token;
	private String sign;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTimestamp() {
		timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
