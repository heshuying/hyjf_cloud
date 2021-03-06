package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class IncreaseInterestRepayRequest extends BasePage implements Serializable {

    /** 借款编号(检索用) */
    private String borrowNidSrch;

    /** 还款状态(检索用) */
    private String repayStatusSrch;

    /** 出借开始时间(检索用) */
    private String timeStartSrch;

    /** 出借结束时间(检索用) */
    private String timeEndSrch;

    public int limitStart = -1;
    public int limitEnd = -1;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getRepayStatusSrch() {
        return repayStatusSrch;
    }

    public void setRepayStatusSrch(String repayStatusSrch) {
        this.repayStatusSrch = repayStatusSrch;
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

    public int getLimitStart() { return limitStart; }

    public void setLimitStart(int limitStart) { this.limitStart = limitStart; }

    public int getLimitEnd() { return limitEnd; }

    public void setLimitEnd(int limitEnd) { this.limitEnd = limitEnd; }
}
