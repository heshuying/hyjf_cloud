package com.hyjf.am.vo.api;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by future on 2019/3/23.
 */
public class OperMonthPerformanceDataVO implements Serializable {
    /**
     * 规模业绩
     */
    private BigDecimal invest = BigDecimal.ZERO;

    /**
     * 年化业绩
     */
    private BigDecimal yearAmount = BigDecimal.ZERO;
    /**
     * 站岗资金
     */
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     * 增资
     */
    private BigDecimal additionalShare = BigDecimal.ZERO;
    /**
     * 提现金额
     */
    private BigDecimal withdraw = BigDecimal.ZERO;
    /**
     * 充值金额
     */
    private BigDecimal recharge = BigDecimal.ZERO;
    /**
     * 总待还金额
     */
    private BigDecimal allWaitRepay = BigDecimal.ZERO;
    /**
     * 已还金额
     */
    private BigDecimal allRepay = BigDecimal.ZERO;

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(BigDecimal yearAmount) {
        this.yearAmount = yearAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAdditionalShare() {
        return additionalShare;
    }

    public void setAdditionalShare(BigDecimal additionalShare) {
        this.additionalShare = additionalShare;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    public BigDecimal getAllWaitRepay() {
        return allWaitRepay;
    }

    public void setAllWaitRepay(BigDecimal allWaitRepay) {
        this.allWaitRepay = allWaitRepay;
    }

    public BigDecimal getAllRepay() {
        return allRepay;
    }

    public void setAllRepay(BigDecimal allRepay) {
        this.allRepay = allRepay;
    }
}
