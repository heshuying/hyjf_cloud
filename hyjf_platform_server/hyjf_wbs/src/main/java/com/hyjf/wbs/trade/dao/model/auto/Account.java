package com.hyjf.wbs.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Account implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 用户电子账户号(开户后,维护)
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 资金总额
     *
     * @mbggenerated
     */
    private BigDecimal total;

    /**
     * 可用金额
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 可提现
     *
     * @mbggenerated
     */
    private BigDecimal balanceCash;

    /**
     * 不可提现
     *
     * @mbggenerated
     */
    private BigDecimal balanceFrost;

    /**
     * 冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal frost;

    /**
     * 待收金额
     *
     * @mbggenerated
     */
    private BigDecimal await;

    /**
     * 待还金额
     *
     * @mbggenerated
     */
    private BigDecimal repay;

    /**
     * 计划累计收益
     *
     * @mbggenerated
     */
    private BigDecimal planRepayInterest;

    /**
     * 计划可用余额
     *
     * @mbggenerated
     */
    private BigDecimal planBalance;

    /**
     * 计划冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal planFrost;

    /**
     * 计划累计加入金额
     *
     * @mbggenerated
     */
    private BigDecimal planAccedeTotal;

    /**
     * 计划加入可用余额
     *
     * @mbggenerated
     */
    private BigDecimal planAccedeBalance;

    /**
     * 投资汇添金标的投资的未放款金额
     *
     * @mbggenerated
     */
    private BigDecimal planAccedeFrost;

    /**
     * 计划累计待收总额
     *
     * @mbggenerated
     */
    private BigDecimal planAccountWait;

    /**
     * 计划累计待收本金
     *
     * @mbggenerated
     */
    private BigDecimal planCapitalWait;

    /**
     * 计划待收利息
     *
     * @mbggenerated
     */
    private BigDecimal planInterestWait;

    /**
     * 银行总资产
     *
     * @mbggenerated
     */
    private BigDecimal bankTotal;

    /**
     * 江西银行可用余额
     *
     * @mbggenerated
     */
    private BigDecimal bankBalance;

    /**
     * 银行冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal bankFrost;

    /**
     * 银行待还本息
     *
     * @mbggenerated
     */
    private BigDecimal bankWaitRepay;

    /**
     * 银行待还本金
     *
     * @mbggenerated
     */
    private BigDecimal bankWaitCapital;

    /**
     * 银行待还利息
     *
     * @mbggenerated
     */
    private BigDecimal bankWaitInterest;

    /**
     * 待还垫付机构金额
     *
     * @mbggenerated
     */
    private BigDecimal bankWaitRepayOrg;

    /**
     * 银行累计收益
     *
     * @mbggenerated
     */
    private BigDecimal bankInterestSum;

    /**
     * 银行累计投资
     *
     * @mbggenerated
     */
    private BigDecimal bankInvestSum;

    /**
     * 银行待收总额
     *
     * @mbggenerated
     */
    private BigDecimal bankAwait;

    /**
     * 银行待收本金
     *
     * @mbggenerated
     */
    private BigDecimal bankAwaitCapital;

    /**
     * 银行待收利息
     *
     * @mbggenerated
     */
    private BigDecimal bankAwaitInterest;

    /**
     * 银行垫付机构待收垫付总额
     *
     * @mbggenerated
     */
    private BigDecimal bankAwaitOrg;

    /**
     * 江西银行可提现金额(银行电子账户余额)
     *
     * @mbggenerated
     */
    private BigDecimal bankBalanceCash;

    /**
     * 江西银行冻结金额(银行电子账户冻结金额)
     *
     * @mbggenerated
     */
    private BigDecimal bankFrostCash;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
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