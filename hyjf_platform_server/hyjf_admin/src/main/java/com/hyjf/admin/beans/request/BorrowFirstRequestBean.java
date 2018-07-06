/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFirstRequestBean, v0.1 2018/7/3 14:50
 */
public class BorrowFirstRequestBean extends BaseRequest implements Serializable {
    /**
     * 检索条件 借款编号
     */
    private String borrowNidSrch;
    /**
     * 检索条件 借款期限
     */
    private String borrowPeriod;

    /**
     * 检索条件 用户名
     */
    private String usernameSrch;

    /**
     * 检索条件 资金来源
     */
    private String instCodeSrch;

    /**
     *标的初审状态
     */
    private String verifyStatusSrch;

    /**
     * 检索条件 发布时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 发布时间结束
     */
    private String timeEndSrch;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getVerifyStatusSrch() {
        return verifyStatusSrch;
    }

    public void setVerifyStatusSrch(String verifyStatusSrch) {
        this.verifyStatusSrch = verifyStatusSrch;
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
}
