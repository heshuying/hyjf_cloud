package com.hyjf.cs.trade.bean;

import java.math.BigDecimal;

/**
 * aems用户还款result
 */
public class AemsRepayResultBean extends BaseResultBean {

    /**
     * 电子账号
     */
    private String accountId;
    /**
     * 项目编号
     */
    private String productId;
    /**
     * 还款期数
     */
    private String repayPeriods;
    /**
     * 应还本金
     */
    private BigDecimal repayAccount;
    /**
     * 应还利息
     */
    private BigDecimal repayAccountInterest;
    /**
     * 应还服务费
     */
    private BigDecimal serviceFee;
    /**
     * 提前还款减息
     */
    private BigDecimal reduceInterest;
    /**
     * 逾期违约金
     */
    private BigDecimal dueServiceFee;
    /**
     * 还款总额
     */
    private BigDecimal repayAccountAll;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRepayPeriods() {
        return repayPeriods;
    }

    public void setRepayPeriods(String repayPeriods) {
        this.repayPeriods = repayPeriods;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getReduceInterest() {
        return reduceInterest;
    }

    public void setReduceInterest(BigDecimal reduceInterest) {
        this.reduceInterest = reduceInterest;
    }

    public BigDecimal getDueServiceFee() {
        return dueServiceFee;
    }

    public void setDueServiceFee(BigDecimal dueServiceFee) {
        this.dueServiceFee = dueServiceFee;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }
}
