package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountWithdraw implements Serializable {
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 交易凭证号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 提现状态:0:提现中,1:失败,2:成功
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 所属银行
     *
     * @mbggenerated
     */
    private String bank;

    private Integer bankId;

    /**
     * 总额
     *
     * @mbggenerated
     */
    private BigDecimal total;

    /**
     * 到账总额
     *
     * @mbggenerated
     */
    private BigDecimal credited;

    /**
     * 手续费
     *
     * @mbggenerated
     */
    private String fee;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    private String remark;

    /**
     * 0pc 1WX 2AND 3IOS 4other
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 提现原因
     *
     * @mbggenerated
     */
    private String reason;

    /**
     * 银行存管提现标志位 1为银行存管 0非银行存管
     *
     * @mbggenerated
     */
    private Integer bankFlag;

    /**
     * 银行电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 交易日期
     *
     * @mbggenerated
     */
    private Integer txDate;

    /**
     * 交易时间
     *
     * @mbggenerated
     */
    private Integer txTime;

    /**
     * 交易流水号
     *
     * @mbggenerated
     */
    private Integer seqNo;

    /**
     * 交易日期+交易时间+交易流水号
     *
     * @mbggenerated
     */
    private String bankSeqNo;

    /**
     * 提现类型 0主动提现  1代提现
     *
     * @mbggenerated
     */
    private Integer withdrawType;

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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCredited() {
        return credited;
    }

    public void setCredited(BigDecimal credited) {
        this.credited = credited;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getBankFlag() {
        return bankFlag;
    }

    public void setBankFlag(Integer bankFlag) {
        this.bankFlag = bankFlag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
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

    public Integer getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(Integer withdrawType) {
        this.withdrawType = withdrawType;
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