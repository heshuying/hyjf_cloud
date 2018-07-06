/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFirstCustomizeVO, v0.1 2018/7/3 15:15
 */
public class BorrowFirstCustomizeVO extends BaseVO implements Serializable {
    /**
     * 借款编码
     */
    private String borrowNid;
    /**
     * 借款标题
     */
    private String borrowName;
    /**
     * 借款用户
     */
    private String username;
    /**
     * 借款金额
     */
    private String account;
    /**
     * 年利率
     */
    private String borrowApr;
    /**
     * 借款期限
     */
    private String borrowPeriod;
    /**
     * 应还本息
     */
    private String repayAccount;
    /**
     * 借到时间
     */
    private String createTime;
    /**
     * 应还时间
     */
    private String repayTime;
    /**
     * 定时发标时间
     */
    private String ontime;
    /**
     * 预约开始时间
     */
    private String bookingBeginTime;
    /**
     * 预约截止时间
     */
    private String bookingEndTime;

    /**
     * 状态 未交保证金 OR 已交保证金
     */
    private String status;
    /**
     * 未交保证金 OR 已交保证金
     */
    private String isBail;
    /**
     * 添加时间
     */
    private String addtime;
    /**
     * 初审状态
     */
    private String verifyStatus;

    /** 初审状态名称 */
    private String verifyStatusName;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 资产来源
     */
    private String instName;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(String repayAccount) {
        this.repayAccount = repayAccount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    public String getBookingBeginTime() {
        return bookingBeginTime;
    }

    public void setBookingBeginTime(String bookingBeginTime) {
        this.bookingBeginTime = bookingBeginTime;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsBail() {
        return isBail;
    }

    public void setIsBail(String isBail) {
        this.isBail = isBail;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyStatusName() {
        return verifyStatusName;
    }

    public void setVerifyStatusName(String verifyStatusName) {
        this.verifyStatusName = verifyStatusName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}
