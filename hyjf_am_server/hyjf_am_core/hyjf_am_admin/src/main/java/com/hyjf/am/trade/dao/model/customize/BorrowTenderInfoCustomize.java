package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

public class BorrowTenderInfoCustomize {

	/**
	 * 投资编号
	 */
	private String nid;
	
	/**
	 * 投资金额
	 */
	private BigDecimal tenderAccount;
	
	/**
	 * 标的编号
	 */
	private String borrowNid;
	
	/**
	 * 标的类别
	 */
	private Integer projectType;
	
	/**
	 * 借款期限（如3个月或6个月的标，非分期的期数 谨记）
	 */
	private Integer borrowPeriod;
	
	/**
	 * 用户编号
	 */
	private Integer userId;
	
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	
	private Integer addtime;

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public BigDecimal getTenderAccount() {
		return tenderAccount;
	}

	public void setTenderAccount(BigDecimal tenderAccount) {
		this.tenderAccount = tenderAccount;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(Integer borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
}
