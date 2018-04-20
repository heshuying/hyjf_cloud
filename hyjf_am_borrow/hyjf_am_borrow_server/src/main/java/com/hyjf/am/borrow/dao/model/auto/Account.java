package com.hyjf.am.borrow.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
    private Integer id;

    private Integer userId;

    private BigDecimal total;

    private BigDecimal income;

    private BigDecimal expend;

    private BigDecimal balance;

    private BigDecimal balanceCash;

    private BigDecimal balanceFrost;

    private BigDecimal frost;

    private BigDecimal await;

    private BigDecimal repay;

    private BigDecimal frostCash;

    private Integer isUpdate;

    private Integer isok;

    private BigDecimal recMoney;

    private BigDecimal fee;

    private BigDecimal inMoney;

    private Integer inMoneyFlag;

    private BigDecimal version;

    private BigDecimal planAccedeTotal;

    private BigDecimal planBalance;

    private BigDecimal planFrost;

    private BigDecimal planAccedeBalance;

    private BigDecimal planAccedeFrost;

    private BigDecimal planRepayInterest;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpend() {
        return expend;
    }

    public void setExpend(BigDecimal expend) {
        this.expend = expend;
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

    public BigDecimal getFrostCash() {
        return frostCash;
    }

    public void setFrostCash(BigDecimal frostCash) {
        this.frostCash = frostCash;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getIsok() {
        return isok;
    }

    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    public BigDecimal getRecMoney() {
        return recMoney;
    }

    public void setRecMoney(BigDecimal recMoney) {
        this.recMoney = recMoney;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getInMoney() {
        return inMoney;
    }

    public void setInMoney(BigDecimal inMoney) {
        this.inMoney = inMoney;
    }

    public Integer getInMoneyFlag() {
        return inMoneyFlag;
    }

    public void setInMoneyFlag(Integer inMoneyFlag) {
        this.inMoneyFlag = inMoneyFlag;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public BigDecimal getPlanAccedeTotal() {
        return planAccedeTotal;
    }

    public void setPlanAccedeTotal(BigDecimal planAccedeTotal) {
        this.planAccedeTotal = planAccedeTotal;
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

    public BigDecimal getPlanRepayInterest() {
        return planRepayInterest;
    }

    public void setPlanRepayInterest(BigDecimal planRepayInterest) {
        this.planRepayInterest = planRepayInterest;
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
}