/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version HjhCreditTenderSumVO.java, v0.1 2018年8月20日 下午4:55:04
 */
public class HjhCreditTenderSumVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 承接本金总计
	 */
	private String sumAssignCapital;
	
	/**
	 * 承接利息总计
	 */
	private String sumAssignInterestAdvance;
	
	/**
	 * 支付金额总计
	 */
	private String sumAssignPay;
	
	/**
	 * 服务费总计
	 */
	private String sumAssignServiceFee;

	public String getSumAssignCapital() {
		return sumAssignCapital;
	}

	public void setSumAssignCapital(String sumAssignCapital) {
		this.sumAssignCapital = sumAssignCapital;
	}

	public String getSumAssignInterestAdvance() {
		return sumAssignInterestAdvance;
	}

	public void setSumAssignInterestAdvance(String sumAssignInterestAdvance) {
		this.sumAssignInterestAdvance = sumAssignInterestAdvance;
	}

	public String getSumAssignPay() {
		return sumAssignPay;
	}

	public void setSumAssignPay(String sumAssignPay) {
		this.sumAssignPay = sumAssignPay;
	}

	public String getSumAssignServiceFee() {
		return sumAssignServiceFee;
	}

	public void setSumAssignServiceFee(String sumAssignServiceFee) {
		this.sumAssignServiceFee = sumAssignServiceFee;
	}
}
