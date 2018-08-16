/**
 * Description:可转让投资列表实体类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class TenderCreditCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款标题
	 */
	private String borrowName;
	/**
	 * 借款编号
	 */
	private String borrowNid;
	/**
	 * 项目类型
	 */
	private String borrowType;
	/**
	 * 年化收益
	 */
	private String borrowApr;
	/**
	 * 借款总金额
	 */
	private String borrowAccount;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 投资时间
	 */
	private String tenderTime;
	/**
	 * 投资订单号
	 */
	private String tenderNid;
	/**
	 * 投资本金
	 */
	private String investAccount;
	/**
	 * 最后还款时间
	 */
	private String repayLastTime;
	/**
	 * 债转本金
	 */
	private String creditAccount;
	/**
	 * 剩余天数
	 */
	private String lastDays;
	/**
	 * 融资期限
	 */
	private String borrowPeriod;
	/**
	 * 持有期限
	 */
	private String tenderPeriod;
	/**
	 * 时间
	 */
	private String recoverTime;
	/**
	 * 当前期数
	 */
	private String periodNow;

	/**
	 * 还款方式名
	 */
	private String borrowStyleName;

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getTenderTime() {
		return tenderTime;
	}

	public void setTenderTime(String tenderTime) {
		this.tenderTime = tenderTime;
	}

	public String getTenderNid() {
		return tenderNid;
	}

	public void setTenderNid(String tenderNid) {
		this.tenderNid = tenderNid;
	}

	public String getInvestAccount() {
		return investAccount;
	}

	public void setInvestAccount(String investAccount) {
		this.investAccount = investAccount;
	}

	public String getRepayLastTime() {
		return repayLastTime;
	}

	public void setRepayLastTime(String repayLastTime) {
		this.repayLastTime = repayLastTime;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getLastDays() {
		return lastDays;
	}

	public void setLastDays(String lastDays) {
		this.lastDays = lastDays;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getTenderPeriod() {
		return tenderPeriod;
	}

	public void setTenderPeriod(String tenderPeriod) {
		this.tenderPeriod = tenderPeriod;
	}

	public String getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(String recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getPeriodNow() {
		return periodNow;
	}

	public void setPeriodNow(String periodNow) {
		this.periodNow = periodNow;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

}
