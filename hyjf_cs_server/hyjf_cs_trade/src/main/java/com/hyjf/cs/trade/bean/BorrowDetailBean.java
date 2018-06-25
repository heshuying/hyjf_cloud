
package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class BorrowDetailBean implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7415746042913857105L;
	/**
	 * id唯一标识
	 */
	private String id;
	/**
	 * 页面key(中文展示)
	 */
	private String key;
	/**
	 * 结果值
	 */
	private String val;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
	
}
