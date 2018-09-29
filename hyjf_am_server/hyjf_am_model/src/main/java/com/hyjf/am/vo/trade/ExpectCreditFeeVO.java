/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 债转详细预计服务费计算
 */
public class ExpectCreditFeeVO extends BaseVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String creditAccount;
	private String creditInterest;
	private String assignInterestAdvance;
	private String assignPayInterest;
	private String assignPay;
	private String assignInterest;
	private String creditCapital;
	private String creditPrice;
	private String expectInterest;
	private String creditFee;

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getCreditInterest() {
		return creditInterest;
	}

	public void setCreditInterest(String creditInterest) {
		this.creditInterest = creditInterest;
	}

	public String getAssignInterestAdvance() {
		return assignInterestAdvance;
	}

	public void setAssignInterestAdvance(String assignInterestAdvance) {
		this.assignInterestAdvance = assignInterestAdvance;
	}

	public String getAssignPayInterest() {
		return assignPayInterest;
	}

	public void setAssignPayInterest(String assignPayInterest) {
		this.assignPayInterest = assignPayInterest;
	}

	public String getAssignPay() {
		return assignPay;
	}

	public void setAssignPay(String assignPay) {
		this.assignPay = assignPay;
	}

	public String getAssignInterest() {
		return assignInterest;
	}

	public void setAssignInterest(String assignInterest) {
		this.assignInterest = assignInterest;
	}

	public String getCreditCapital() {
		return creditCapital;
	}

	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}

	public String getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(String creditPrice) {
		this.creditPrice = creditPrice;
	}

	public String getExpectInterest() {
		return expectInterest;
	}

	public void setExpectInterest(String expectInterest) {
		this.expectInterest = expectInterest;
	}

	public String getCreditFee() {
		return creditFee;
	}

	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
	}
}
