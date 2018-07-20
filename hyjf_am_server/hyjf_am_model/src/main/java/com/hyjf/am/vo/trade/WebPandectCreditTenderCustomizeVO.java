package com.hyjf.am.vo.trade;

import java.math.BigDecimal;

public class WebPandectCreditTenderCustomizeVO {
	/**
	 * 已还利息
	 */
	private BigDecimal creditInterestYes= new BigDecimal(0);
	/**
	 * 支付金额
	 */
	private BigDecimal creditAssign= new BigDecimal(0);
	/**
	 * 待还本金 （投资本金-已还本金）
	 */
	private BigDecimal creditCapitalWait= new BigDecimal(0);
	/**
	 * 待还利息 （回收总额-投资本金-已还利息）
	 */
	private BigDecimal creditInterestWait= new BigDecimal(0);
	/**
	 * 累计本金
	 */
	private BigDecimal creditCapital= new BigDecimal(0);
	
	
	public BigDecimal getCreditInterestYes() {
		return creditInterestYes;
	}
	public void setCreditInterestYes(BigDecimal creditInterestYes) {
		this.creditInterestYes = creditInterestYes;
	}
	public BigDecimal getCreditAssign() {
		return creditAssign;
	}
	public void setCreditAssign(BigDecimal creditAssign) {
		this.creditAssign = creditAssign;
	}
	public BigDecimal getCreditCapitalWait() {
		return creditCapitalWait;
	}
	public void setCreditCapitalWait(BigDecimal creditCapitalWait) {
		this.creditCapitalWait = creditCapitalWait;
	}
	public BigDecimal getCreditInterestWait() {
		return creditInterestWait;
	}
	public void setCreditInterestWait(BigDecimal creditInterestWait) {
		this.creditInterestWait = creditInterestWait;
	}
	public BigDecimal getCreditCapital() {
		return creditCapital;
	}
	public void setCreditCapital(BigDecimal creditCapital) {
		this.creditCapital = creditCapital;
	}
	
	
	
}
