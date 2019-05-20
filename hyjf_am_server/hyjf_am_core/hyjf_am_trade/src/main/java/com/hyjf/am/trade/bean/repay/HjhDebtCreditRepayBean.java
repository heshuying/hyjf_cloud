package com.hyjf.am.trade.bean.repay;

import com.hyjf.am.vo.trade.repay.DebtCreditRepayVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class HjhDebtCreditRepayBean extends DebtCreditRepayVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6191363977885745665L;
	private BigDecimal assignTotal;
	// 原始提前还款利息
	public BigDecimal chargeOriginalInterest;

	public BigDecimal getAssignTotal() {
		return assignTotal;
	}

	public void setAssignTotal(BigDecimal assignTotal) {
		this.assignTotal = assignTotal;
	}

	public BigDecimal getChargeOriginalInterest() {
		return chargeOriginalInterest;
	}

	public void setChargeOriginalInterest(BigDecimal chargeOriginalInterest) {
		this.chargeOriginalInterest = chargeOriginalInterest;
	}
}
