package com.hyjf.am.vo.api;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthDataStatisticsVO implements Serializable {

    //新老用户组
    private String customerGroup;
    // 坐席
    private String currentOwner;
    // 年化业绩
    private BigDecimal yearMoney = BigDecimal.ZERO;
    // 充值
    private BigDecimal recharge = BigDecimal.ZERO;
    // 回款
    private BigDecimal received = BigDecimal.ZERO;
    // 提现
    private BigDecimal withdraw = BigDecimal.ZERO;
    // 站岗资金
    private BigDecimal guardFund = BigDecimal.ZERO;
    // 增资
    private BigDecimal additionalShare = BigDecimal.ZERO;
    // 提现率
    private BigDecimal extractionRate = BigDecimal.ZERO;

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public BigDecimal getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(BigDecimal yearMoney) {
        this.yearMoney = yearMoney;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public BigDecimal getGuardFund() {
        return guardFund;
    }

    public void setGuardFund(BigDecimal guardFund) {
        this.guardFund = guardFund;
    }

    public BigDecimal getAdditionalShare() {
        return additionalShare;
    }

    public void setAdditionalShare(BigDecimal additionalShare) {
        this.additionalShare = additionalShare;
    }

    public BigDecimal getExtractionRate() {
        return extractionRate;
    }

    public void setExtractionRate(BigDecimal extractionRate) {
        this.extractionRate = extractionRate;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public BigDecimal getReceived() {
        return received;
    }

    public void setReceived(BigDecimal received) {
        this.received = received;
    }
}
