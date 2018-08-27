package com.hyjf.cs.trade.vo;

import java.io.Serializable;

public class BaseResultBean implements Serializable {

	private static final long serialVersionUID = -2899752944266497051L;

	private String status;
	private String statusDesc;

	public BaseResultBean() {
		this.status = "000";
		this.statusDesc = "成功";
	}

	public BaseResultBean(String status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
