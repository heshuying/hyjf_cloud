package com.hyjf.am.vo.trade;

import java.math.BigDecimal;

public class WebPandectRecoverMoneyCustomizeVO {
	/**
	 * 已回收本金
	 */
	private BigDecimal recoverCapital= new BigDecimal(0);
	/**
	 * 已回收利息
	 */
	private BigDecimal recoverInterest= new BigDecimal(0);
	
	
	public BigDecimal getRecoverInterest() {
		return recoverInterest;
	}
	public void setRecoverInterest(BigDecimal recoverInterest) {
		this.recoverInterest = recoverInterest;
	}
	public BigDecimal getRecoverCapital() {
		return recoverCapital;
	}
	public void setRecoverCapital(BigDecimal recoverCapital) {
		this.recoverCapital = recoverCapital;
	}
	
	
	
}
