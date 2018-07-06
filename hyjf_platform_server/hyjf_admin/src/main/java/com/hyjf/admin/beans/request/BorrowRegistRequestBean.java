/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowRegistRequestBean, v0.1 2018/6/29 14:19
 */
public class BorrowRegistRequestBean extends BaseRequest implements Serializable {

    /**
     * 检索条件 借款编号
     */
    private String borrowNidSrch;

    /**
     * 检索条件 借款标题
     */
    private String borrowNameSrch;

    /**
     * 检索条件 借款标题
     */
    private String borrowPeriodSrch;

    /**
     * 检索条件 用户名
     */
    private String usernameSrch;


    /**
     * 检索条件 发布时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 发布时间结束
     */
    private String timeEndSrch;

    /**
     * 标的备案状态
     */
    private String registStatusSrch;

    /**
     * 标的备案状态
     */
    private String projectTypeSrch;

    /**
     * 标的备案状态
     */
    private String borrowStyleSrch;

    /**
     * 借款人用户名
     */
    private String userName;

    public String getRegistStatusSrch() {
        return registStatusSrch;
    }

    public void setRegistStatusSrch(String registStatusSrch) {
        this.registStatusSrch = registStatusSrch;
    }

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

    public String getBorrowNameSrch() {
        return borrowNameSrch;
    }

    public void setBorrowNameSrch(String borrowNameSrch) {
        this.borrowNameSrch = borrowNameSrch;
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

    public String getBorrowPeriodSrch() {
        return borrowPeriodSrch;
    }

    public void setBorrowPeriodSrch(String borrowPeriodSrch) {
        this.borrowPeriodSrch = borrowPeriodSrch;
    }

    public String getProjectTypeSrch() {
        return projectTypeSrch;
    }

    public void setProjectTypeSrch(String projectTypeSrch) {
        this.projectTypeSrch = projectTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
