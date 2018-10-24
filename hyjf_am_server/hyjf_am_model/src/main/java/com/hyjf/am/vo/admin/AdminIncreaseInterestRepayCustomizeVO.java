package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 融通宝加息还款
 * 
 * @ClassName AdminIncreaseInterestRepayCustomize
 * @author liuyang
 * @date 2017年1月4日 下午4:06:00
 */
public class AdminIncreaseInterestRepayCustomizeVO extends BaseVO implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -7406958514084024467L;

	/** 借款编号 */
	private String borrowNid;

	/** 借款期限 */
	private String borrowPeriod;

	/** 还款期数 */
	private String repayPeriod;

	/** 借款方式 */
	private String borrowStyle;

	/** 还款方式 */
	private String repayStyleName;

	/** 投资人用户名 */
	private String investUserName;

	/** 投资人用户ID */
	private String investUserId;

	/** 投资id */
	private String investId;

	/** 年化收益率 */
	private String borrowApr;

	/** 产品加息收益率 */
	private String borrowExtraYield;

	/** 还款本金 */
	private String repayCapital;

	/** 应还加息收益 */
	private String repayInterest;

	/** 应还时间 */
	private String repayTime;

	/** 转账状态 */
	private String repayStatus;

	/*-----add by LSY START------*/
	/** 应还本金合计 */
	private String sumRepayCapital;
	/** 应还加息收益合计 */
	private String sumLoanInterest;
	/** 应还加息收益合计 */
	private String sumRepayInterest;
	/*-----add by LSY END------*/
	private String repayActionTime;
	private BigDecimal repayInterestYes;

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getRepayStyleName() {
		return repayStyleName;
	}

	public void setRepayStyleName(String repayStyleName) {
		this.repayStyleName = repayStyleName;
	}

	public String getInvestUserName() {
		return investUserName;
	}

	public void setInvestUserName(String investUserName) {
		this.investUserName = investUserName;
	}

	public String getInvestUserId() {
		return investUserId;
	}

	public void setInvestUserId(String investUserId) {
		this.investUserId = investUserId;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(String repayCapital) {
		this.repayCapital = repayCapital;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}
	public String getBorrowPeriodByStyle(){
		if ("endday".equals(this.borrowStyle)) {
			return this.borrowPeriod + "天";
		} else {
			return this.borrowPeriod + "个月";
		}
	}
	public String getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(String repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	/**
	 * sumRepayCapital
	 * @return the sumRepayCapital
	 */
		
	public String getSumRepayCapital() {
		return sumRepayCapital;
			
	}

	/**
	 * @param sumRepayCapital the sumRepayCapital to set
	 */
		
	public void setSumRepayCapital(String sumRepayCapital) {
		this.sumRepayCapital = sumRepayCapital;
			
	}

	/**
	 * sumLoanInterest
	 * @return the sumLoanInterest
	 */
		
	public String getSumLoanInterest() {
		return sumLoanInterest;
			
	}

	/**
	 * @param sumLoanInterest the sumLoanInterest to set
	 */
		
	public void setSumLoanInterest(String sumLoanInterest) {
		this.sumLoanInterest = sumLoanInterest;
			
	}

	/**
	 * sumRepayInterest
	 * @return the sumRepayInterest
	 */
		
	public String getSumRepayInterest() {
		return sumRepayInterest;
			
	}

	/**
	 * @param sumRepayInterest the sumRepayInterest to set
	 */
		
	public void setSumRepayInterest(String sumRepayInterest) {
		this.sumRepayInterest = sumRepayInterest;
			
	}

	public String getRepayActionTime() {
		return repayActionTime;
	}

	public void setRepayActionTime(String repayActionTime) {
		this.repayActionTime = repayActionTime;
	}

	public BigDecimal getRepayInterestYes() {
		return repayInterestYes;
	}

	public void setRepayInterestYes(BigDecimal repayInterestYes) {
		this.repayInterestYes = repayInterestYes;
	}
}
