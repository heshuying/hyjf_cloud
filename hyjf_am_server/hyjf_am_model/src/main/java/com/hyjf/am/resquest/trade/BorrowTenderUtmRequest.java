/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

import java.util.List;

/**
 * @author fuqiang
 * @version BorrowTenderUtmRequest, v0.1 2018/7/17 9:38
 */
public class BorrowTenderUtmRequest extends Request {
	Integer sourceId;

	List<Integer> list;

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}
}
