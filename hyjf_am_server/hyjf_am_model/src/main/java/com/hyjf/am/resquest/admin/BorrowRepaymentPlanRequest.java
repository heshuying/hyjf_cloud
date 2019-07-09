package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

/**
 * @author pangchengchao
 * @version BorrowRepaymentPlanRequest, v0.1 2018/7/5 11:27
 */
public class BorrowRepaymentPlanRequest extends BasePage {

    /**
     * 投资nid 检索条件
     */
    private String nid;
    /**
     * 资产来源 检索条件
     */
    private String instCodeSrch;
    /**
     * 借款编号 检索条件
     */
    private String borrowNid;
    /**
     * 借款标题 检索条件
     */
    private String borrowName;
    /**
     * 还款状态 检索条件
     */
    private String status;

    private String actulRepayTimeStartSrch;

    private String actulRepayTimeEndSrch;


    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeStartSrch;
    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeEndSrch;

    /**
     * 发布日期 检索条件
     */
    private String verifyTimeStartSrch;
    /**
     * 发布日期 检索条件
     */
    private String verifyTimeEndSrch;

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

	/**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;
    

    public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
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

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
