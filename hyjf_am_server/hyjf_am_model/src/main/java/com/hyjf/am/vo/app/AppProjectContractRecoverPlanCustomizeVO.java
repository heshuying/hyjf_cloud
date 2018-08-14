/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.app;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectContractRecoverPlanCustomizeVO implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2708328205111331639L;
	/* 期数 repayPeriod */
	private String repayPeriod;
	/* 应还本金 repayCapital */
	private String repayCapital;
	/* 应还利息 repayInterest */
	private String repayInterest;
	/* 应还日起 repayTime */
	private String repayTime;

	public AppProjectContractRecoverPlanCustomizeVO() {
		super();
	}

	public String getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(String repayPeriod) {
		this.repayPeriod = repayPeriod;
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

}
