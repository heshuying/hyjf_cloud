package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version BorrowRepaymentPlanRequestBean, v0.1 2018/7/5 10:36
 */
public class BorrowRepaymentPlanRequestBean extends BaseRequest implements Serializable {
    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeStartSrch;
    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeEndSrch;
    /**
     * 检索条件 实际还款时间开始
     */
    private String actulRepayTimeStartSrch;
    /**
     * 检索条件 实际还款时间结束
     */
    private String actulRepayTimeEndSrch;
    /**
     * 发布日期 检索条件
     */
    private String verifyTimeStartSrch;
    /**
     * 发布日期 检索条件
     */
    private String verifyTimeEndSrch;

    /**
     * 机构名称代号 检索条件
     */
    private String instCodeSrch;

    private String borrowNid;// 项目编号
    private String borrowName;// 借款标题
    private String statusSrch;// 还款状态
    
    
    private String planNid;// 智投编号
    private String repayPeriod;// 还款期数
    private String period;// 借款期数
    private String borrowUserName;//借款人
    private String autoRepay;// 还款方式
    private String extraYieldRepayStatus;// 期次状态
    
    
    public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(String repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getAutoRepay() {
		return autoRepay;
	}

	public void setAutoRepay(String autoRepay) {
		this.autoRepay = autoRepay;
	}

	public String getExtraYieldRepayStatus() {
		return extraYieldRepayStatus;
	}

	public void setExtraYieldRepayStatus(String extraYieldRepayStatus) {
		this.extraYieldRepayStatus = extraYieldRepayStatus;
	}

	public String getRepayLastTimeStartSrch() {
        return repayLastTimeStartSrch;
    }

    public void setRepayLastTimeStartSrch(String repayLastTimeStartSrch) {
        this.repayLastTimeStartSrch = repayLastTimeStartSrch;
    }

    public String getRepayLastTimeEndSrch() {
        return repayLastTimeEndSrch;
    }

    public void setRepayLastTimeEndSrch(String repayLastTimeEndSrch) {
        this.repayLastTimeEndSrch = repayLastTimeEndSrch;
    }


    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getVerifyTimeStartSrch() {
        return verifyTimeStartSrch;
    }

    public void setVerifyTimeStartSrch(String verifyTimeStartSrch) {
        this.verifyTimeStartSrch = verifyTimeStartSrch;
    }

    public String getVerifyTimeEndSrch() {
        return verifyTimeEndSrch;
    }

    public void setVerifyTimeEndSrch(String verifyTimeEndSrch) {
        this.verifyTimeEndSrch = verifyTimeEndSrch;
    }

    public String getActulRepayTimeStartSrch() {
        return actulRepayTimeStartSrch;
    }

    public void setActulRepayTimeStartSrch(String actulRepayTimeStartSrch) {
        this.actulRepayTimeStartSrch = actulRepayTimeStartSrch;
    }

    public String getActulRepayTimeEndSrch() {
        return actulRepayTimeEndSrch;
    }

    public void setActulRepayTimeEndSrch(String actulRepayTimeEndSrch) {
        this.actulRepayTimeEndSrch = actulRepayTimeEndSrch;
    }
}
