package com.hyjf.am.vo.trade;

import java.math.BigDecimal;

public class WebPandectBorrowRecoverCustomizeVO {
	/**
	 * 已债转金额
	 */
	private BigDecimal creditAmount= new BigDecimal(0);
	/**
	 * 已债转总利息（含垫付）
	 */
	private BigDecimal creditInterestAmount= new BigDecimal(0);
	
	
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	public BigDecimal getCreditInterestAmount() {
		return creditInterestAmount;
	}
	public void setCreditInterestAmount(BigDecimal creditInterestAmount) {
		this.creditInterestAmount = creditInterestAmount;
	}
	
	
	
}
