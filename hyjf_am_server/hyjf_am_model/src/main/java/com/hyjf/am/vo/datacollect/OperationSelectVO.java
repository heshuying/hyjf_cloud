package com.hyjf.am.vo.datacollect;

import java.io.Serializable;

/**
 * 运营报告下拉框
 */
public class OperationSelectVO implements Serializable {
	
	private  String code;
	private  String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
