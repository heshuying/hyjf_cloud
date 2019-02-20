package com.hyjf.am.vo.trade.account;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountListVO extends BaseVO implements Serializable {
    private Integer id;

    private String nid;

    private String accedeOrderId;

    private Integer isShow;

    private Integer userId;

    private BigDecimal amount;

    private Integer type;

    private String trade;

    private String tradeCode;

    private BigDecimal total;

    private BigDecimal balance;

    private BigDecimal frost;

    private BigDecimal planFrost;

    private BigDecimal await;

    private BigDecimal repay;

    private String remark;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private String operator;

    private String ip;

    private Integer isUpdate;

    private Integer baseUpdate;

    private BigDecimal interest;

    private Integer web;

    private BigDecimal planBalance;

    private Integer isBank;

    private String accountId;

    private String seqNo;

    private BigDecimal bankTotal;

    private BigDecimal bankWaitRepay;

    private BigDecimal bankBalance;

    private BigDecimal bankFrost;

    private BigDecimal bankWaitCapital;

    private BigDecimal bankWaitInterest;

    private BigDecimal bankAwaitCapital;

    private BigDecimal bankAwaitInterest;

    private BigDecimal bankAwait;

    private BigDecimal bankInterestSum;

    private BigDecimal bankInvestSum;

    private Integer checkStatus;

    private Integer tradeStatus;

    private Integer txDate;

    private Integer txTime;

    private String bankSeqNo;

    private Integer checkDate;

    private BigDecimal checkBalance;

    private Integer accountDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
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

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }

    public BigDecimal getPlanFrost() {
        return planFrost;
    }

    public void setPlanFrost(BigDecimal planFrost) {
        this.planFrost = planFrost;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getBaseUpdate() {
        return baseUpdate;
    }

    public void setBaseUpdate(Integer baseUpdate) {
        this.baseUpdate = baseUpdate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public BigDecimal getPlanBalance() {
        return planBalance;
    }

    public void setPlanBalance(BigDecimal planBalance) {
        this.planBalance = planBalance;
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public BigDecimal getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(BigDecimal bankTotal) {
        this.bankTotal = bankTotal;
    }

    public BigDecimal getBankWaitRepay() {
        return bankWaitRepay;
    }

    public void setBankWaitRepay(BigDecimal bankWaitRepay) {
        this.bankWaitRepay = bankWaitRepay;
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

    public BigDecimal getBankAwait() {
        return bankAwait;
    }

    public void setBankAwait(BigDecimal bankAwait) {
        this.bankAwait = bankAwait;
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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo == null ? null : bankSeqNo.trim();
    }

    public Integer getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Integer checkDate) {
        this.checkDate = checkDate;
    }

    public BigDecimal getCheckBalance() {
        return checkBalance;
    }

    public void setCheckBalance(BigDecimal checkBalance) {
        this.checkBalance = checkBalance;
    }

    public Integer getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Integer accountDate) {
        this.accountDate = accountDate;
    }
}