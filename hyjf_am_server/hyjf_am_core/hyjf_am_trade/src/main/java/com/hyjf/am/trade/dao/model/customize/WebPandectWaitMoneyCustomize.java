package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

public class WebPandectWaitMoneyCustomize {
	/**
	 * 待收本金
	 */
	private BigDecimal waitCapital;
	/**
	 * 待收利息
	 */
	private BigDecimal recoverInterest;
	
	
	public BigDecimal getWaitCapital() {
		return waitCapital;
	}
	public void setWaitCapital(BigDecimal waitCapital) {
		this.waitCapital = waitCapital;
	}
	public BigDecimal getRecoverInterest() {
		return recoverInterest;
	}
	public void setRecoverInterest(BigDecimal recoverInterest) {
		this.recoverInterest = recoverInterest;
	}
	
	
	
}
