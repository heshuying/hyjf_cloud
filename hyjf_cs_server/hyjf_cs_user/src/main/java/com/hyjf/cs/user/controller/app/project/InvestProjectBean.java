package com.hyjf.cs.user.controller.app.project;

import java.io.Serializable;

public class InvestProjectBean implements Serializable {

	/**
	 * 此处为属性说明
	 */
	private static final long serialVersionUID = 7149122333518461124L;

	/** 项目编号 */
	private String borrowNid;

	/** 投资单号 */
	private String tenderNid;

	/** 优惠券编号 */
	private String couponCode;
	/** 投资类型 3产品加息 */
	private String investType;

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

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

}
