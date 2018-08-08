package com.hyjf.cs.user.vo;

import java.math.BigDecimal;

/**
 * 账户总览接口返回数据结构
 * Created by cuigq on 2018/2/1.
 */
public class MyProfileVO {

    private UserAccountInfoVO userAccountInfo;//用户账户信息

    private BigDecimal bankBalance;  //可用余额

    private BigDecimal accountTotle; // 账户总资产

    private BigDecimal waitInterest; // 待收收益

    private BigDecimal interestTotle; // 累计收益

    private BigDecimal chinapnrBalance; //汇付账户余额（非汇付用户时无效）

    private Integer couponValidCount; //我的优惠卷

    private BigDecimal bankAwait; //我的散标

    private BigDecimal planAccountWait; //我的计划

    public UserAccountInfoVO getUserAccountInfo() {
        return userAccountInfo;
    }

    public void setUserAccountInfo(UserAccountInfoVO userAccountInfo) {
        this.userAccountInfo = userAccountInfo;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getAccountTotle() {
        return accountTotle;
    }

    public void setAccountTotle(BigDecimal accountTotle) {
        this.accountTotle = accountTotle;
    }

    public BigDecimal getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(BigDecimal waitInterest) {
        this.waitInterest = waitInterest;
    }

    public BigDecimal getInterestTotle() {
        return interestTotle;
    }

    public void setInterestTotle(BigDecimal interestTotle) {
        this.interestTotle = interestTotle;
    }

    public BigDecimal getChinapnrBalance() {
        return chinapnrBalance;
    }

    public void setChinapnrBalance(BigDecimal chinapnrBalance) {
        this.chinapnrBalance = chinapnrBalance;
    }

    public Integer getCouponValidCount() {
        return couponValidCount;
    }

    public void setCouponValidCount(Integer couponValidCount) {
        this.couponValidCount = couponValidCount;
    }

    public BigDecimal getBankAwait() {
        return bankAwait;
    }

    public void setBankAwait(BigDecimal bankAwait) {
        this.bankAwait = bankAwait;
    }

    public BigDecimal getPlanAccountWait() {
        return planAccountWait;
    }

    public void setPlanAccountWait(BigDecimal planAccountWait) {
        this.planAccountWait = planAccountWait;
    }

}
