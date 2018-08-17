package com.hyjf.am.trade.dao.model.customize;

/**
 * @author jijun
 * @date 20180624
 */

public class WebProjectRepayListCustomize {
	
	//还款明细的id
	public String id;
	// 期数
	public String projectPeriod;
	// 待收本息
	public String projectTotal;
	// 待收本金
	public String projectCapital;
	// 待收利息
	public String projectInterest;
	// 管理费
	public String projectFee;
	// 待收时间
	public String repayTime;
	   // 待收时间
    public String repayDay;
	// 实际还款
	public String repayTotal;
	// 还款状态
	public String status;
	//罚息总额  提前还款用（0或 负数）
	public String chargeInterest;
	//逾期利息
	public String lateInterest;
	//延期利息
	public String dalayInterest;
	/**
	 * 构造方法
	 */
	public WebProjectRepayListCustomize() {
		super();
	}

	public String getProjectPeriod() {
		return projectPeriod;
	}

	public void setProjectPeriod(String projectPeriod) {
		this.projectPeriod = projectPeriod;
	}

	public String getProjectTotal() {
		return projectTotal;
	}

	public void setProjectTotal(String projectTotal) {
		this.projectTotal = projectTotal;
	}

	public String getProjectCapital() {
		return projectCapital;
	}

	public void setProjectCapital(String projectCapital) {
		this.projectCapital = projectCapital;
	}

	public String getProjectInterest() {
		return projectInterest;
	}

	public void setProjectInterest(String projectInterest) {
		this.projectInterest = projectInterest;
	}

	public String getProjectFee() {
		return projectFee;
	}

	public void setProjectFee(String projectFee) {
		this.projectFee = projectFee;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChargeInterest() {
		return chargeInterest;
	}

	public void setChargeInterest(String chargeInterest) {
		this.chargeInterest = chargeInterest;
	}

	public String getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(String lateInterest) {
		this.lateInterest = lateInterest;
	}

	public String getDalayInterest() {
		return dalayInterest;
	}

	public void setDalayInterest(String dalayInterest) {
		this.dalayInterest = dalayInterest;
	}

    public String getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(String repayDay) {
        this.repayDay = repayDay;
    }

}
