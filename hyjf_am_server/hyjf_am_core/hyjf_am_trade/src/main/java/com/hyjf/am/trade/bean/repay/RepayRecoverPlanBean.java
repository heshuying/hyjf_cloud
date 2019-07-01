package com.hyjf.am.trade.bean.repay;

import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepayRecoverPlanBean extends BorrowRecoverPlan implements Serializable {
	private static final long serialVersionUID = 3456910400807898597L;

	private BigDecimal recoverTotal;

	/** 债转还款 */
	private List<RepayCreditRepayBean> creditRepayList = new ArrayList<RepayCreditRepayBean>();

	/** 计划类债转还款 */
	private List<HjhDebtCreditRepayBean> hjhCreditRepayList = new ArrayList<HjhDebtCreditRepayBean>();

	private BigDecimal recoverAccountOld;

	private BigDecimal chargeInterestOld;

	private BigDecimal delayInterestOld;

	private BigDecimal lateInterestOld;

	private BigDecimal recoverAccountYesOld;

	private BigDecimal recoverCapitalOld;

	private BigDecimal creditAmountOld;

	private BigDecimal chargePenaltyInterestOld;

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

	public BigDecimal getRecoverAccountOld() {
		return recoverAccountOld;
	}

	public void setRecoverAccountOld(BigDecimal recoverAccountOld) {
		this.recoverAccountOld = recoverAccountOld;
	}

	public BigDecimal getChargeInterestOld() {
		return chargeInterestOld;
	}

	public void setChargeInterestOld(BigDecimal chargeInterestOld) {
		this.chargeInterestOld = chargeInterestOld;
	}

	public BigDecimal getDelayInterestOld() {
		return delayInterestOld;
	}

	public void setDelayInterestOld(BigDecimal delayInterestOld) {
		this.delayInterestOld = delayInterestOld;
	}

	public BigDecimal getLateInterestOld() {
		return lateInterestOld;
	}

	public void setLateInterestOld(BigDecimal lateInterestOld) {
		this.lateInterestOld = lateInterestOld;
	}

	public BigDecimal getRecoverAccountYesOld() {
		return recoverAccountYesOld;
	}

	public void setRecoverAccountYesOld(BigDecimal recoverAccountYesOld) {
		this.recoverAccountYesOld = recoverAccountYesOld;
	}

	public BigDecimal getRecoverCapitalOld() {
		return recoverCapitalOld;
	}

	public void setRecoverCapitalOld(BigDecimal recoverCapitalOld) {
		this.recoverCapitalOld = recoverCapitalOld;
	}

	public BigDecimal getCreditAmountOld() {
		return creditAmountOld;
	}

	public void setCreditAmountOld(BigDecimal creditAmountOld) {
		this.creditAmountOld = creditAmountOld;
	}

	public BigDecimal getChargePenaltyInterestOld() {
		return chargePenaltyInterestOld;
	}

	public void setChargePenaltyInterestOld(BigDecimal chargePenaltyInterestOld) {
		this.chargePenaltyInterestOld = chargePenaltyInterestOld;
	}
}
