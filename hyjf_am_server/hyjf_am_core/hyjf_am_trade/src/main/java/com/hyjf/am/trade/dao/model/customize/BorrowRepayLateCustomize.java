/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjun
 * @version BorrowRepayLateCustomize, v0.1 2019/3/21 11:11
 */
public class BorrowRepayLateCustomize implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 标的编号
     */
    private String borrowNid;

    /**
     * 标的期数
     */
    private Integer recoverPeriod;

    /**
     * 应该本息
     */
    private BigDecimal recoverAccount;

    /**
     * 应该利息
     */
    private BigDecimal recoverInterest;

    /**
     * 应该本金
     */
    private BigDecimal recoverCapital;

    /**
     * 已承接债转本金
     */
    private BigDecimal creditAmount;

    /**
     * 投资订单号
     */
    private String tenderNid;

    /**
     * 汇计划加入订单号
     */
    private String accedeOrderId;

    /**
     * 债转状态
     */
    private Integer creditStatus;

    /**
     * 应还款时间
     */
    private Date recoverTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }
}
