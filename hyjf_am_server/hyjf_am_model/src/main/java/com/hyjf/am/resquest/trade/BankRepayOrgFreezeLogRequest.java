package com.hyjf.am.resquest.trade;

import java.math.BigDecimal;

/**
 * @author wgx
 * @date 2018/10/11
 */
public class BankRepayOrgFreezeLogRequest {
    private Integer repayUserId;

    private String repayUserName;

    private Integer borrowUserId;

    private String borrowUserName;

    private String account;

    private String orderId;

    private String borrowNid;

    private String planNid;

    private String instCode;

    private BigDecimal amount;

    private BigDecimal amountFreeze;

    private BigDecimal repayAccount;

    private BigDecimal repayFee;

    private BigDecimal lowerInterest;

    private BigDecimal penaltyAmount;

    private BigDecimal defaultInterest;

    private String borrowPeriod;

    private Integer totalPeriod;

    private Integer currentPeriod;

    private Integer allRepayFlag;

    private Integer delFlag;

    private Integer latePeriod;

    public Integer getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(Integer repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName;
    }

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountFreeze() {
        return amountFreeze;
    }

    public void setAmountFreeze(BigDecimal amountFreeze) {
        this.amountFreeze = amountFreeze;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public BigDecimal getLowerInterest() {
        return lowerInterest;
    }

    public void setLowerInterest(BigDecimal lowerInterest) {
        this.lowerInterest = lowerInterest;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(BigDecimal defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getAllRepayFlag() {
        return allRepayFlag;
    }

    public void setAllRepayFlag(Integer allRepayFlag) {
        this.allRepayFlag = allRepayFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }
}
