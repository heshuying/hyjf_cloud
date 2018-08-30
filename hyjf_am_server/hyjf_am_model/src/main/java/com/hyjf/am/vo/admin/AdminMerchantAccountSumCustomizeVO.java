package com.hyjf.am.vo.admin;

import java.io.Serializable;

public class AdminMerchantAccountSumCustomizeVO implements Serializable {

	/**
	 * serialVersionUID:序列化id
	 */

	private static final long serialVersionUID = 1741582712557173951L;

	private String accountBalanceSum;

	private String availableBalanceSum;

	private String frostSum;

	public AdminMerchantAccountSumCustomizeVO() {
		super();
	}

	public String getAccountBalanceSum() {
		return accountBalanceSum;
	}

	public void setAccountBalanceSum(String accountBalanceSum) {
		this.accountBalanceSum = accountBalanceSum;
	}

	public String getAvailableBalanceSum() {
		return availableBalanceSum;
	}

	public void setAvailableBalanceSum(String availableBalanceSum) {
		this.availableBalanceSum = availableBalanceSum;
	}

	public String getFrostSum() {
		return frostSum;
	}

	public void setFrostSum(String frostSum) {
		this.frostSum = frostSum;
	}
	
}
