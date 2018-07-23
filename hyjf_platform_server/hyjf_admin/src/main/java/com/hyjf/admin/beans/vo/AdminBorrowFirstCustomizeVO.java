/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version AdminBorrowFirstCustomizeVO, v0.1 2018/7/23 11:11
 */
public class AdminBorrowFirstCustomizeVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "借款标题")
    private String borrowName;

    @ApiModelProperty(value = "借款用户")
    private String username;

    @ApiModelProperty(value = "借款金额")
    private String account;

    @ApiModelProperty(value = "借款利率")
    private String borrowApr;

    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;

    @ApiModelProperty(value = "应还本息")
    private String repayAccount;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "应还时间")
    private String repayTime;

    @ApiModelProperty(value = "定时发标时间")
    private String ontime;

    @ApiModelProperty(value = "预约开始时间")
    private String bookingBeginTime;

    @ApiModelProperty(value = "预约截止时间")
    private String bookingEndTime;

    //没用到，暂时删除
//    private String status;
//    private String isBail;

    @ApiModelProperty(value = "初审状态")
    private String verifyStatus;

    @ApiModelProperty(value = "初审状态名称")
    private String verifyStatusName;

    @ApiModelProperty(value = "项目类型")
    private String projectType;

    @ApiModelProperty(value = "资产来源")
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
