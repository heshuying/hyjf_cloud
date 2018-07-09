/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFullRequest, v0.1 2018/7/6 9:49
 */
public class BorrowFullRequest extends BasePage implements Serializable {
    private String borrowNidSrch;

    private String usernameSrch;

    private String timeStartSrch;

    private String timeEndSrch;

    private String reverifyRemark;

    private int limitStart = -1;

    private int limitEnd = -1;

    private String currUserId;

    private String currUserName;

    private BorrowVO borrowVO;

    private BorrowInfoVO borrowInfoVO;

    private String accountId;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
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

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark;
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

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    public String getCurrUserName() {
        return currUserName;
    }

    public void setCurrUserName(String currUserName) {
        this.currUserName = currUserName;
    }

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public BorrowInfoVO getBorrowInfoVO() {
        return borrowInfoVO;
    }

    public void setBorrowInfoVO(BorrowInfoVO borrowInfoVO) {
        this.borrowInfoVO = borrowInfoVO;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
