/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean.api;

import java.io.Serializable;

import com.hyjf.cs.trade.bean.BaseBean;

/**
 * @author libin
 * 自动投资请求参数
 * @version AutoTenderRequestBean.java, v0.1 2018年8月24日 上午10:48:25
 */
public class AutoTenderRequestBean extends BaseBean implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String couponGrantId;
	
	private String borrowNid;
	
	private String money;
	
	private String accountId;

	public String getCouponGrantId() {
		return couponGrantId;
	}

	public void setCouponGrantId(String couponGrantId) {
		this.couponGrantId = couponGrantId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
