package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class IncreaseInterestRepayPlanRequest extends BasePage implements Serializable {

    /** 借款编号(检索用) */
    private String borrowNidSrch;

    /** 用户名(检索用) */
    private String userNameSrch;

    /** 投资开始时间(检索用) */
    private String timeStartSrch;

    /** 投资结束时间(检索用) */
    private String timeEndSrch;

    /**转账状态*/
    private String repayStatusSrch;

    public int limitStart = -1;
    public int limitEnd = -1;

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

    public String getRepayStatusSrch() {
        return repayStatusSrch;
    }

    public void setRepayStatusSrch(String repayStatusSrch) {
        this.repayStatusSrch = repayStatusSrch;
    }

    public int getLimitStart() { return limitStart; }

    public void setLimitStart(int limitStart) { this.limitStart = limitStart; }

    public int getLimitEnd() { return limitEnd; }

    public void setLimitEnd(int limitEnd) { this.limitEnd = limitEnd; }
}
