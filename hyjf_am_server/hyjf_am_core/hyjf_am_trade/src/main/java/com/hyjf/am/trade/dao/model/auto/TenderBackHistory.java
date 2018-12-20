package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class TenderBackHistory implements Serializable {
    private Integer id;

    /**
     * 撤销方 0：运营方 1：出借方
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 借款标题
     *
     * @mbggenerated
     */
    private String borrowName;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 借到金额
     *
     * @mbggenerated
     */
    private String accountYes;

    /**
     * 出借用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 撤销金额
     *
     * @mbggenerated
     */
    private String amount;

    /**
     * 出借订单号
     *
     * @mbggenerated
     */
    private String ordId;

    /**
     * 解冻订单号
     *
     * @mbggenerated
     */
    private String trxId;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String notes;

    /**
     * 操作用户
     *
     * @mbggenerated
     */
    private String createUser;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAccountYes() {
        return accountYes;
    }

    public void setAccountYes(String accountYes) {
        this.accountYes = accountYes == null ? null : accountYes.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId == null ? null : ordId.trim();
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId == null ? null : trxId.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}