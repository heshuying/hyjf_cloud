/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;

import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsRequest, v0.1 2018/7/10 18:09
 */
public class ProtocolsRequest extends BasePage {
	private String ids;

	/** 列表list */
	private List<FddTempletCustomizeVO> recordList;

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<FddTempletCustomizeVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<FddTempletCustomizeVO> recordList) {
		this.recordList = recordList;
	}
}
