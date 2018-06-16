package com.hyjf.callcenter.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author libin
 * @version HztInvestBean, v0.1 2018/6/5
 */
public class HztInvestBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 借款编号
	 */
	private String borrowNid;
	/**
	 * 年化利率
	 */
	private String borrowApr;
	/**
	 * 产品加息收益率
	 */
	private BigDecimal borrowExtraYield;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 投资金额
	 */
	private String account;
	/**
	 * 还款方式
	 */
	private String borrowStyleName;
	/**
	 * 操作平台
	 */
	private String operatingDeck;
	/**
	 * 投资方式
	 */
	private String investType;
	/**
	 * 投资时间
	 */
	private String addtime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBorrowNid() {
		return borrowNid;
	}
	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	public String getBorrowApr() {
		return borrowApr;
	}
	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}
	public BigDecimal getBorrowExtraYield() {
		return borrowExtraYield;
	}
	public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}
	public String getBorrowPeriod() {
		return borrowPeriod;
	}
	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBorrowStyleName() {
		return borrowStyleName;
	}
	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}
	public String getOperatingDeck() {
		return operatingDeck;
	}
	public void setOperatingDeck(String operatingDeck) {
		this.operatingDeck = operatingDeck;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
