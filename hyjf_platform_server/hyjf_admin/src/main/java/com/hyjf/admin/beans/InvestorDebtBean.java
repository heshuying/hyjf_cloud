/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangjun
 * @version InvestorDebtBean, v0.1 2018/7/11 9:21
 */
public class InvestorDebtBean implements Serializable {
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 电子账号
     */
    private String accountid;

    /**
     * 标的编号
     */
    private String borrownid;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * nid
     */
    private String nid;
    /**
     * 投标日期
     */
    private String buyDate;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 交易金额
     */
    private BigDecimal txAmount;
    /**
     * 预期年化收益率
     */
    private BigDecimal yield;
    /**
     * 预期收益
     */
    private BigDecimal forIncome;
    /**
     * 预期本息收益
     */
    private BigDecimal intTotal;
    /**
     * 实际收益
     */
    private BigDecimal income;
    /**
     * 实际收益符号
     */
    private String incFlag;
    /**
     * 到期日
     */
    private String endDate;
    /**
     * 到期日
     */
    private String state;

    private BigDecimal sumTxAmount;
    private BigDecimal sumForIncome;
    private BigDecimal sumIntTotal;
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

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getBorrownid() {
        return borrownid;
    }

    public void setBorrownid(String borrownid) {
        this.borrownid = borrownid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
