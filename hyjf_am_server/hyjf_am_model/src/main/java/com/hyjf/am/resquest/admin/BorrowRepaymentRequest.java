package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

/**
 * @author pangchengchao
 * @version BorrowRecoverRequest, v0.1 2018/7/2 14:25
 */
public class BorrowRepaymentRequest extends BasePage {

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
     * 状态 检索条件
     */
    private String statusSrch;
    /**
     * 计划编号 检索条件
     */
    private String planNidSrch;
    /**
     * 计划加入订单号 检索条件
     */
    private String accedeOrderIdSrch;
    /**
     * 资产来源 检索条件
     */
    private String instCodeSrch;

    /**
     * 借款编号 检索条件
     */
    private String borrowNid;
    /**
     * 借款编号 检索条件
     */
    private String borrowName;
    /**
     * 借款期限 检索条件
     */
    private String borrowPeriod;

    /**
     * 借款人 检索条件
     */
    private String borrowUserName;

    /**
     * 还款方式 检索条件
     */
    private String repayStyleType;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    private String planNid;// 智投编号
    private String repayPeriod;// 还款期数
    private String period;// 借款期数
  //  private String borrowUserName;//借款人
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

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
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

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getRepayStyleType() {
        return repayStyleType;
    }

    public void setRepayStyleType(String repayStyleType) {
        this.repayStyleType = repayStyleType;
    }

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

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }
}
