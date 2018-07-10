/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.admin;

import java.io.Serializable;

import com.hyjf.am.trade.dao.model.auto.FddTemplet;

/**
 * @author fuqiang
 * @version FddTempletCustomize, v0.1 2018/7/10 16:07
 */
public class FddTempletCustomize extends FddTemplet implements Serializable {
	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 协议类型名称
	 */
	private String protocolTypeName;

	/**
	 * CA认证名称
	 */
	private String caFlagName;

	protected int limitStart = -1;

	protected int limitEnd = -1;

	public String getProtocolTypeName() {
		return protocolTypeName;
	}

	public void setProtocolTypeName(String protocolTypeName) {
		this.protocolTypeName = protocolTypeName;
	}

	public String getCaFlagName() {
		return caFlagName;
	}

	public void setCaFlagName(String caFlagName) {
		this.caFlagName = caFlagName;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}
