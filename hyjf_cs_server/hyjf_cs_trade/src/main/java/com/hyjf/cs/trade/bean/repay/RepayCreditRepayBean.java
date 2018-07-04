package com.hyjf.cs.trade.bean.repay;

import com.hyjf.am.vo.trade.CreditRepayVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class RepayCreditRepayBean extends CreditRepayVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6191363977885745665L;
	private BigDecimal assignTotal;

	public BigDecimal getAssignTotal() {
		return assignTotal;
	}

	public void setAssignTotal(BigDecimal assignTotal) {
		this.assignTotal = assignTotal;
	}
	


}
