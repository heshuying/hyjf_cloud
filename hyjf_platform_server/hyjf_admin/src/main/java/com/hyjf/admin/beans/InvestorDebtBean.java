/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangjun
 * @version InvestorDebtBean, v0.1 2018/7/11 9:21
 */
public class InvestorDebtBean implements Serializable {
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "电子账号")
    private String accountId;

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "投资订单号")
    private String nid;

    @ApiModelProperty(value = "投标日期")
    private String buyDate;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal txAmount;

    @ApiModelProperty(value = "预期年化收益率")
    private BigDecimal yield;

    @ApiModelProperty(value = "预期收益")
    private BigDecimal forIncome;

    @ApiModelProperty(value = "预期本息收益")
    private BigDecimal intTotal;

    @ApiModelProperty(value = "实际收益")
    private BigDecimal income;

    @ApiModelProperty(value = "实际收益符号")
    private String incFlag;

    @ApiModelProperty(value = "到期日")
    private String endDate;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal sumTxAmount;

    @ApiModelProperty(value = "预期收益")
    private BigDecimal sumForIncome;

    @ApiModelProperty(value = "预期本息收益")
    private BigDecimal sumIntTotal;

    @ApiModelProperty(value = "实际收益")
    private BigDecimal sumIncome;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getForIncome() {
        return forIncome;
    }

    public void setForIncome(BigDecimal forIncome) {
        this.forIncome = forIncome;
    }

    public BigDecimal getIntTotal() {
        return intTotal;
    }

    public void setIntTotal(BigDecimal intTotal) {
        this.intTotal = intTotal;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getIncFlag() {
        return incFlag;
    }

    public void setIncFlag(String incFlag) {
        this.incFlag = incFlag;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getSumTxAmount() {
        return sumTxAmount;
    }

    public void setSumTxAmount(BigDecimal sumTxAmount) {
        this.sumTxAmount = sumTxAmount;
    }

    public BigDecimal getSumForIncome() {
        return sumForIncome;
    }

    public void setSumForIncome(BigDecimal sumForIncome) {
        this.sumForIncome = sumForIncome;
    }

    public BigDecimal getSumIntTotal() {
        return sumIntTotal;
    }

    public void setSumIntTotal(BigDecimal sumIntTotal) {
        this.sumIntTotal = sumIntTotal;
    }

    public BigDecimal getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(BigDecimal sumIncome) {
        this.sumIncome = sumIncome;
    }
}
