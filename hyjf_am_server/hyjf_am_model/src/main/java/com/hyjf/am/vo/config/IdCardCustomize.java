package com.hyjf.am.vo.config;

import java.io.Serializable;

public class IdCardCustomize implements Serializable {

	private String bm;
	private String area;

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}