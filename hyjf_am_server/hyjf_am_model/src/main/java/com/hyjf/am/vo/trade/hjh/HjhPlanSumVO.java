/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version HjhPlanSumVO.java, v0.1 2018年7月6日 上午10:51:32
 */
public class HjhPlanSumVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 待还总额累计 原 sumHjhPlan
	 */
	private String sumWaitTotal;
	
	/**
	 * 开放额度累计
	 */
	private String sumOpenAccount;
	
	/**
	 * 累计加入金额累计
	 */
	private String sumJoinTotal;

	public String getSumWaitTotal() {
		return sumWaitTotal;
	}

	public void setSumWaitTotal(String sumWaitTotal) {
		this.sumWaitTotal = sumWaitTotal;
	}

	public String getSumOpenAccount() {
		return sumOpenAccount;
	}

	public void setSumOpenAccount(String sumOpenAccount) {
		this.sumOpenAccount = sumOpenAccount;
	}

	public String getSumJoinTotal() {
		return sumJoinTotal;
	}

	public void setSumJoinTotal(String sumJoinTotal) {
		this.sumJoinTotal = sumJoinTotal;
	}
}
