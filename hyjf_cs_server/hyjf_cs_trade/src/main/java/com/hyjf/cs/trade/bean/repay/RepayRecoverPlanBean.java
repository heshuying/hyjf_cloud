package com.hyjf.cs.trade.bean.repay;

import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepayRecoverPlanBean extends BorrowRecoverPlanVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3456910400807898597L;
	
	private BigDecimal recoverTotal;
	
	/** 债转还款 */
	private List<RepayCreditRepayBean> creditRepayList = new ArrayList<RepayCreditRepayBean>();

	/** 计划类债转还款 */
	private List<HjhDebtCreditRepayBean> hjhCreditRepayList = new ArrayList<HjhDebtCreditRepayBean>();


	public BigDecimal getRecoverTotal() {
		return recoverTotal;
	}

	public void setRecoverTotal(BigDecimal recoverTotal) {
		this.recoverTotal = recoverTotal;
	}

	public List<RepayCreditRepayBean> getCreditRepayList() {
		return creditRepayList;
	}

	public void setCreditRepayList(List<RepayCreditRepayBean> creditRepayList) {
		this.creditRepayList = creditRepayList;
	}

	public List<HjhDebtCreditRepayBean> getHjhCreditRepayList() {
		return hjhCreditRepayList;
	}

	public void setHjhCreditRepayList(List<HjhDebtCreditRepayBean> hjhCreditRepayList) {
		this.hjhCreditRepayList = hjhCreditRepayList;
	}
}
