package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ManualReverse implements Serializable {
    private Integer id;

    /**
     * 原交易流水号
     *
     * @mbggenerated
     */
    private Integer seqNo;

    /**
     * 交易流水号
     *
     * @mbggenerated
     */
    private String bankSeqNo;

    /**
     * 交易时间
     *
     * @mbggenerated
     */
    private Date txTime;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 资金托管平台 0:汇付，1:江西银行
     *
     * @mbggenerated
     */
    private Integer isBank;

    /**
     * 收支类型 0收入 1支出
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private String transType;

    /**
     * 操作金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 操作状态 0 成功 1失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建用户ID
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo == null ? null : bankSeqNo.trim();
    }

    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}