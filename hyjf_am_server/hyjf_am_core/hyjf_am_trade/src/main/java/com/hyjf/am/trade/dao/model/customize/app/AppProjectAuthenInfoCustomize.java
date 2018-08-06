/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize.app;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectAuthenInfoCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2708328205111331639L;
	/* 证件名称 authenName */
	private String authenName;
	/* 认证时间 identifyTime */
	private String authenTime;
	/* 认证状态 identifyStatus */
	private String authenStatus;

	public AppProjectAuthenInfoCustomize() {
		super();
	}

	public String getAuthenName() {
		return authenName;
	}

	public void setAuthenName(String authenName) {
		this.authenName = authenName;
	}

	public String getAuthenTime() {
		return authenTime;
	}

	public void setAuthenTime(String authenTime) {
		this.authenTime = authenTime;
	}

	public String getAuthenStatus() {
		return authenStatus;
	}

	public void setAuthenStatus(String authenStatus) {
		this.authenStatus = authenStatus;
	}

}
