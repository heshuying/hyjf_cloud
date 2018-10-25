package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version BorrowRepayAgreementCustomize, v0.1 2018/8/14 16:57
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementCustomizeVO extends BaseVO implements Serializable {

    @ApiModelProperty(value = "实际还款人(垫付机构)id")
    private String repayUserId;

    @ApiModelProperty(value = "实际还款人(垫付机构)")
    private String repayUsername;

    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "nid")
    private String nid;

    @ApiModelProperty(value = "还款状态")
    private String repayStatus;

    @ApiModelProperty(value = "是否是分期")
    private boolean isMonth;

    @ApiModelProperty(value = "是否申请了垫付协议")
    private int applyagreements;

    @ApiModelProperty(value = "资产来源")
    private String borrowProjectSource;

    @ApiModelProperty(value = "期数")
    private int repayPeriod;

    @ApiModelProperty(value = "垫付时间")
    private String repayYseTime;

    @ApiModelProperty(value = "垫付总额")
    private BigDecimal repayCapital;

    @ApiModelProperty(value = "借款人ID")
    private int userId;

    @ApiModelProperty(value = "借款人")
    private String userName;

    @ApiModelProperty(value = "检索条件 limitStart")
    private int limitStart = -1;

    @ApiModelProperty(value = "检索条件 limitEnd")
    private int limitEnd = -1;

    public String getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(String repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUsername() {
        return repayUsername;
    }

    public void setRepayUsername(String repayUsername) {
        this.repayUsername = repayUsername;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public boolean isMonth() {
        return isMonth;
    }

    public void setMonth(boolean month) {
        isMonth = month;
    }

    public int getApplyagreements() {
        return applyagreements;
    }

    public void setApplyagreements(int applyagreements) {
        this.applyagreements = applyagreements;
    }

    public String getBorrowProjectSource() {
        return borrowProjectSource;
    }

    public void setBorrowProjectSource(String borrowProjectSource) {
        this.borrowProjectSource = borrowProjectSource;
    }

    public int getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(int repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getRepayYseTime() {
        return repayYseTime;
    }

    public void setRepayYseTime(String repayYseTime) {
        this.repayYseTime = repayYseTime;
    }

    public BigDecimal getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(BigDecimal repayCapital) {
        this.repayCapital = repayCapital;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
