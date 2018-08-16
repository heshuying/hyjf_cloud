/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.bean;

import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version SynBalanceBean, v0.1 2018/7/2 21:36
 */
public class SynBalanceBean {

    /**
     * 入账日期
     */
    private String accDate;
    /**
     * 交易日期
     */
    private String inpDate;
    /**
     * 自然日期
     */
    private String relDate;
    /**
     * 交易时间
     */
    private String inpTime;
    /**
     * 流水号
     */
    private int traceNo;
    /**
     * 电子账号
     */
    private String accountId;
    /**
     * 交易类型
     */
    private String tranType;
    /**
     * 冲正撤销标志 O-原始交易 R-已经冲正或者撤销
     */
    private String orFlag;
    /**
     * 交易金额
     */
    private BigDecimal txAmount;
    /**
     * 交易金额符号   +/-
     */
    private String txFlag;
    /**
     * 交易描述
     */
    private String describe;
    /**
     * 货币代码 156-人民币
     */
    private String currency;
    /**
     * 交易后余额
     */
    private BigDecimal currBal;
    /**
     * 对手电子账号
     */
    private String forAccountId;

    public String getAccDate() {
        return accDate;
    }

    public void setAccDate(String accDate) {
        this.accDate = accDate;
    }

    public String getInpDate() {
        return inpDate;
    }

    public void setInpDate(String inpDate) {
        this.inpDate = inpDate;
    }

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }

    public String getInpTime() {
        return inpTime;
    }

    public void setInpTime(String inpTime) {
        this.inpTime = inpTime;
    }

    public int getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(int traceNo) {
        this.traceNo = traceNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getOrFlag() {
        return orFlag;
    }

    public void setOrFlag(String orFlag) {
        this.orFlag = orFlag;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public String getTxFlag() {
        return txFlag;
    }

    public void setTxFlag(String txFlag) {
        this.txFlag = txFlag;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCurrBal() {
        return currBal;
    }

    public void setCurrBal(BigDecimal currBal) {
        this.currBal = currBal;
    }

    public String getForAccountId() {
        return forAccountId;
    }

    public void setForAccountId(String forAccountId) {
        this.forAccountId = forAccountId;
    }
}
