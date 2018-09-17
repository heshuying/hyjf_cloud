package com.hyjf.cs.trade.bean.repay;

import java.io.Serializable;

public class ProjectRepayListBean implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 4073070104153180850L;



    // 应还本息和
    public String repayAccount;

    // 应还本金
    public String repayCapital;

    // 应还利息
    public String repayInterest;

    // 项目管理费
    public String manageFee;

    // 还款时间
    public String repayTime;

    // 还款期数
    public String repayPeriod;
    
    //本期应还金额
    public String planRepayTotal;
    
    //还款状态
    public String repayStatus;
    
    

    public String getPlanRepayTotal() {
		return planRepayTotal;
	}

	public void setPlanRepayTotal(String planRepayTotal) {
		this.planRepayTotal = planRepayTotal;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public ProjectRepayListBean() {
        super();
    }

	public String getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(String repayAccount) {
        this.repayAccount = repayAccount;
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

    public String getManageFee() {
		return manageFee;
	}

	public void setManageFee(String manageFee) {
		this.manageFee = manageFee;
	}

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

}
