/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

/**
 * @author fuqiang
 * @version ProtocolsRequestBean, v0.1 2018/7/10 16:38
 */
public class ProtocolsRequestBean extends BaseRequest {
	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;

	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	public int getLimitStart() {
		return (getCurrPage() - 1) * (getPageSize());
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return (getCurrPage()) * (getPageSize());
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}
