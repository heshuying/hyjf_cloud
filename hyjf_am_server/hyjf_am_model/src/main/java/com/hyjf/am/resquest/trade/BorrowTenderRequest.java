package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

public class BorrowTenderRequest extends Request {

	private Integer tenderUserId;
	private String borrowNid;
	private String tenderNid;

	public Integer getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(Integer tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getTenderNid() {
		return tenderNid;
	}

	public void setTenderNid(String tenderNid) {
		this.tenderNid = tenderNid;
	}
}
