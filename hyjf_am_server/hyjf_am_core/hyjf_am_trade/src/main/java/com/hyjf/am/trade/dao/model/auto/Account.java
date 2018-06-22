package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String accountId;

    private BigDecimal total;

    private BigDecimal balance;

    private BigDecimal balanceCash;

    private BigDecimal balanceFrost;

    private BigDecimal frost;

    private BigDecimal await;

    private BigDecimal repay;

    private BigDecimal planRepayInterest;

    private BigDecimal planBalance;

    private BigDecimal planFrost;

    private BigDecimal planAccedeTotal;

    private BigDecimal planAccedeBalance;

    private BigDecimal planAccedeFrost;

    private BigDecimal planAccountWait;

    private BigDecimal planCapitalWait;

    private BigDecimal planInterestWait;

    private BigDecimal bankTotal;

    private BigDecimal bankBalance;

    private BigDecimal bankFrost;

    private BigDecimal bankWaitRepay;

    private BigDecimal bankWaitCapital;

    private BigDecimal bankWaitInterest;

    private BigDecimal bankWaitRepayOrg;

    private BigDecimal bankInterestSum;

    private BigDecimal bankInvestSum;

    private BigDecimal bankAwait;

    private BigDecimal bankAwaitCapital;

    private BigDecimal bankAwaitInterest;

    private BigDecimal bankAwaitOrg;

    private BigDecimal bankBalanceCash;

    private BigDecimal bankFrostCash;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(BigDecimal balanceCash) {
        this.balanceCash = balanceCash;
    }

    public BigDecimal getBalanceFrost() {
        return balanceFrost;
    }

    public void setBalanceFrost(BigDecimal balanceFrost) {
        this.balanceFrost = balanceFrost;
    }

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }

    public BigDecimal getAwait() {
        return await;
    }

    public void setAwait(BigDecimal await) {
        this.await = await;
    }

    public BigDecimal getRepay() {
        return repay;
    }

    public void setRepay(BigDecimal repay) {
        this.repay = repay;
    }

    public BigDecimal getPlanRepayInterest() {
        return planRepayInterest;
    }

    public void setPlanRepayInterest(BigDecimal planRepayInterest) {
        this.planRepayInterest = planRepayInterest;
    }

    public BigDecimal getPlanBalance() {
        return planBalance;
    }

    public void setPlanBalance(BigDecimal planBalance) {
        this.planBalance = planBalance;
    }

    public BigDecimal getPlanFrost() {
        return planFrost;
    }

    public void setPlanFrost(BigDecimal planFrost) {
        this.planFrost = planFrost;
    }

    public BigDecimal getPlanAccedeTotal() {
        return planAccedeTotal;
    }

    public void setPlanAccedeTotal(BigDecimal planAccedeTotal) {
        this.planAccedeTotal = planAccedeTotal;
    }

    public BigDecimal getPlanAccedeBalance() {
        return planAccedeBalance;
    }

    public void setPlanAccedeBalance(BigDecimal planAccedeBalance) {
        this.planAccedeBalance = planAccedeBalance;
    }

    public BigDecimal getPlanAccedeFrost() {
        return planAccedeFrost;
    }

    public void setPlanAccedeFrost(BigDecimal planAccedeFrost) {
        this.planAccedeFrost = planAccedeFrost;
    }

    public BigDecimal getPlanAccountWait() {
        return planAccountWait;
    }

    public void setPlanAccountWait(BigDecimal planAccountWait) {
        this.planAccountWait = planAccountWait;
    }

    public BigDecimal getPlanCapitalWait() {
        return planCapitalWait;
    }

    public void setPlanCapitalWait(BigDecimal planCapitalWait) {
        this.planCapitalWait = planCapitalWait;
    }

    public BigDecimal getPlanInterestWait() {
        return planInterestWait;
    }

    public void setPlanInterestWait(BigDecimal planInterestWait) {
        this.planInterestWait = planInterestWait;
    }

    public BigDecimal getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(BigDecimal bankTotal) {
        this.bankTotal = bankTotal;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getBankFrost() {
        return bankFrost;
    }

    public void setBankFrost(BigDecimal bankFrost) {
        this.bankFrost = bankFrost;
    }

    public BigDecimal getBankWaitRepay() {
        return bankWaitRepay;
    }

    public void setBankWaitRepay(BigDecimal bankWaitRepay) {
        this.bankWaitRepay = bankWaitRepay;
    }

    public BigDecimal getBankWaitCapital() {
        return bankWaitCapital;
    }

    public void setBankWaitCapital(BigDecimal bankWaitCapital) {
        this.bankWaitCapital = bankWaitCapital;
    }

    public BigDecimal getBankWaitInterest() {
        return bankWaitInterest;
    }

    public void setBankWaitInterest(BigDecimal bankWaitInterest) {
        this.bankWaitInterest = bankWaitInterest;
    }

    public BigDecimal getBankWaitRepayOrg() {
        return bankWaitRepayOrg;
    }

    public void setBankWaitRepayOrg(BigDecimal bankWaitRepayOrg) {
        this.bankWaitRepayOrg = bankWaitRepayOrg;
    }

    public BigDecimal getBankInterestSum() {
        return bankInterestSum;
    }

    public void setBankInterestSum(BigDecimal bankInterestSum) {
        this.bankInterestSum = bankInterestSum;
    }

    public BigDecimal getBankInvestSum() {
        return bankInvestSum;
    }

    public void setBankInvestSum(BigDecimal bankInvestSum) {
        this.bankInvestSum = bankInvestSum;
    }

    public BigDecimal getBankAwait() {
        return bankAwait;
    }

    public void setBankAwait(BigDecimal bankAwait) {
        this.bankAwait = bankAwait;
    }

    public BigDecimal getBankAwaitCapital() {
        return bankAwaitCapital;
    }

    public void setBankAwaitCapital(BigDecimal bankAwaitCapital) {
        this.bankAwaitCapital = bankAwaitCapital;
    }

    public BigDecimal getBankAwaitInterest() {
        return bankAwaitInterest;
    }

    public void setBankAwaitInterest(BigDecimal bankAwaitInterest) {
        this.bankAwaitInterest = bankAwaitInterest;
    }

    public BigDecimal getBankAwaitOrg() {
        return bankAwaitOrg;
    }

    public void setBankAwaitOrg(BigDecimal bankAwaitOrg) {
        this.bankAwaitOrg = bankAwaitOrg;
    }

    public BigDecimal getBankBalanceCash() {
        return bankBalanceCash;
    }

    public void setBankBalanceCash(BigDecimal bankBalanceCash) {
        this.bankBalanceCash = bankBalanceCash;
    }

    public BigDecimal getBankFrostCash() {
        return bankFrostCash;
    }

    public void setBankFrostCash(BigDecimal bankFrostCash) {
        this.bankFrostCash = bankFrostCash;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}