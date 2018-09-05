/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: AdminPoundageCustomize, v0.1 2018/9/3 15:26
 */
public class AdminPoundageCustomize implements Serializable {

    /**
     * id       db_column: id
     */
    private Integer id;
    /**
     * 用户id       db_column: user_id
     */
    private Integer userId;
    /**
     * 分账配置id       db_column: user_id
     */
    private Integer ledgerId;
    /**
     * 分账总金额       db_column: amount
     */
    private BigDecimal amount;
    /**
     * 分账数量       db_column: quantity
     */
    private Integer quantity;
    /**
     * 分账时间段       db_column: poundage_time
     */
    private String poundageTime;
    /**
     * 分账状态  0：未审核    1：审核通过	2：分账成功		3：分账失败	 4处理中       db_column: status
     */
    private Integer status;
    /**
     * 分账时间       db_column: add_time
     */
    private Integer addTime;
    /**
     * 交易凭证号     db_column: nid
     */
    private String nid;
    /**
     * 请求流水号     db_column: seq_no
     */
    private String seqNo;
    /**
     * 银行订单日期    db_column: tx_date
     */
    private Integer txDate;
    /**
     * 银行订单时间    db_column: tx_time
     */
    private Integer txTime;
    /**
     * 创建时间       db_column: create_time
     */
    private Integer createTime;
    /**
     * 创建人id       db_column: creater
     */
    private Integer creater;
    /**
     * 更新时间       db_column: update_time
     */
    private Integer updateTime;
    /**
     * 修改人id       db_column: updater
     */
    private Integer updater;
    /**
     * 收款方用户名
     */
    private String userName;
    /**
     * 收款方姓名
     */
    private String realName;
    /**
     * 收款方江西银行电子账号
     */
    private String account;
    /*
     * 转出方用户电子账户号
     */
    private String accountId;
    /*
     * 余额
     */
    private String balance;
    /**
     * 密码
     */
    private String password;
    /**
     * 银行返回流水号
     */
    private String bankSeqNo;

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

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPoundageTime() {
        return poundageTime;
    }

    public void setPoundageTime(String poundageTime) {
        this.poundageTime = poundageTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }
}
