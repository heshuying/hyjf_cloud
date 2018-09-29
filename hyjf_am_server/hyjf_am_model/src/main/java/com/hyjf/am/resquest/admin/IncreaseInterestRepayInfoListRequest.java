package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class IncreaseInterestRepayInfoListRequest extends BasePage implements Serializable {

    /**
     * 状态 0为还款中,1为已还款
     */
    private String status;

    /** 借款编号(检索用) */
    private String borrowNidSrch;

    /** 用户名(检索用) */
    private String userNameSrch;

    /** 投资开始时间(检索用) */
    private String timeStartSrch;

    /** 投资结束时间(检索用) */
    private String timeEndSrch;

    /** 投资Id(检索用) */
    private String investIdSrch;

    /** 还款期数(检索用) */
    private String repayPeriodSrch;
    public int limitStart = -1;
    public int limitEnd = -1;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getInvestIdSrch() {
        return investIdSrch;
    }

    public void setInvestIdSrch(String investIdSrch) {
        this.investIdSrch = investIdSrch;
    }

    public String getRepayPeriodSrch() { return repayPeriodSrch; }

    public void setRepayPeriodSrch(String repayPeriodSrch) { this.repayPeriodSrch = repayPeriodSrch; }

    public int getLimitStart() { return limitStart; }

    public void setLimitStart(int limitStart) { this.limitStart = limitStart; }

    public int getLimitEnd() { return limitEnd; }

    public void setLimitEnd(int limitEnd) { this.limitEnd = limitEnd; }
}
