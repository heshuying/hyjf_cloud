/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version HjhAccedeSumVO.java, v0.1 2018年7月10日 上午10:57:17
 */
public class HjhAccedeSumVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 加入金额合计
	 */
	private String sumAccedeAccount;
		/**
	 * 已投资金额
	 */
	private String sumAlreadyInvest;
		/**
	 * 待(收)还总额
	 */
	private String sumWaitTotal;
		/**
	 * 待(收)还本金
	 */
	private String sumWaitCaptical;
		/**
	 * 待(收)还利息
	 */
	private String sumWaitInterest;
	public String getSumAccedeAccount() {
		return sumAccedeAccount;
	}
	public void setSumAccedeAccount(String sumAccedeAccount) {
		this.sumAccedeAccount = sumAccedeAccount;
	}
	public String getSumAlreadyInvest() {
		return sumAlreadyInvest;
	}
	public void setSumAlreadyInvest(String sumAlreadyInvest) {
		this.sumAlreadyInvest = sumAlreadyInvest;
	}
	public String getSumWaitTotal() {
		return sumWaitTotal;
	}
	public void setSumWaitTotal(String sumWaitTotal) {
		this.sumWaitTotal = sumWaitTotal;
	}
	public String getSumWaitCaptical() {
		return sumWaitCaptical;
	}
	public void setSumWaitCaptical(String sumWaitCaptical) {
		this.sumWaitCaptical = sumWaitCaptical;
	}
	public String getSumWaitInterest() {
		return sumWaitInterest;
	}
	public void setSumWaitInterest(String sumWaitInterest) {
		this.sumWaitInterest = sumWaitInterest;
	}
}
