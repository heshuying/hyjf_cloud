package com.hyjf.am.trade.bean.repay;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hyjf.am.vo.trade.CreditRepayVO;

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
