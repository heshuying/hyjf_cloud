package com.hyjf.am.vo.trade;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Api 交易明细查询 VO
 * @Author : huanghui
 */
public class ApiTransactionDetailsCustomizeVO implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 订单号
     */
    private String nid;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 操作金额
     */
    private BigDecimal amount;

    /**
     * 汇添金可用余额
     */
    private BigDecimal planBalance;

    /**
     * 汇添金冻结金额
     */
    private BigDecimal planFrost;
    /**
     * 备注
     */
    private String remark;
    /**
     * 交易时间
     */
    private String createTime;

    /**
     * 账户类型是否银行存管
     */
    private String isBank;

    /**
     * 电子账号
     */
    private String accountId;

    /**
     * 交易流水号
     */
    private String seqNo;

    /**
     * 银行总资产
     */
    private String bankTotal;

    /**
     * 银行存管余额
     */
    private BigDecimal bankBalance;

    /**
     * 银行存管冻结金额
     */
    private BigDecimal bankFrost;

    /**
     * 对账状态
     */
    private String checkStatus;

    /**
     * 交易状态
     */
    private String tradeStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(String bankTotal) {
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

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
}
