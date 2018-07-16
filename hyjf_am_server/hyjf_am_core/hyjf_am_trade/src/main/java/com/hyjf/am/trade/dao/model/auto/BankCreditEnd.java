package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BankCreditEnd implements Serializable {
    private Integer id;

    private String batchNo;

    private String txDate;

    private String txTime;

    private String seqNo;

    private Integer txCounts;

    private Integer userId;

    private String username;

    private Integer tenderUserId;

    private String tenderUsername;

    private String accountId;

    private String tenderAccountId;

    private String orderId;

    private String orgOrderId;

    private String borrowNid;

    private String authCode;

    private Integer creditEndType;

    private Integer received;

    private String checkRetcode;

    private String checkRetmsg;

    private String retcode;

    private String retmsg;

    private Integer sucCounts;

    private Integer failCounts;

    private String state;

    private Integer status;

    private String failmsg;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate == null ? null : txDate.trim();
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime == null ? null : txTime.trim();
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public Integer getTxCounts() {
        return txCounts;
    }

    public void setTxCounts(Integer txCounts) {
        this.txCounts = txCounts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(Integer tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getTenderUsername() {
        return tenderUsername;
    }

    public void setTenderUsername(String tenderUsername) {
        this.tenderUsername = tenderUsername == null ? null : tenderUsername.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getTenderAccountId() {
        return tenderAccountId;
    }

    public void setTenderAccountId(String tenderAccountId) {
        this.tenderAccountId = tenderAccountId == null ? null : tenderAccountId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrgOrderId() {
        return orgOrderId;
    }

    public void setOrgOrderId(String orgOrderId) {
        this.orgOrderId = orgOrderId == null ? null : orgOrderId.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public Integer getCreditEndType() {
        return creditEndType;
    }

    public void setCreditEndType(Integer creditEndType) {
        this.creditEndType = creditEndType;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public String getCheckRetcode() {
        return checkRetcode;
    }

    public void setCheckRetcode(String checkRetcode) {
        this.checkRetcode = checkRetcode == null ? null : checkRetcode.trim();
    }

    public String getCheckRetmsg() {
        return checkRetmsg;
    }

    public void setCheckRetmsg(String checkRetmsg) {
        this.checkRetmsg = checkRetmsg == null ? null : checkRetmsg.trim();
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode == null ? null : retcode.trim();
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg == null ? null : retmsg.trim();
    }

    public Integer getSucCounts() {
        return sucCounts;
    }

    public void setSucCounts(Integer sucCounts) {
        this.sucCounts = sucCounts;
    }

    public Integer getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(Integer failCounts) {
        this.failCounts = failCounts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFailmsg() {
        return failmsg;
    }

    public void setFailmsg(String failmsg) {
        this.failmsg = failmsg == null ? null : failmsg.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}