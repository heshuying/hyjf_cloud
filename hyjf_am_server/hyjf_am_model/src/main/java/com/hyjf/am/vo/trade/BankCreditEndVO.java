/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liubin
 * @version BankCreditEndVO, v0.1 2018/7/10 19:30
 */
public class BankCreditEndVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private Integer state;

    private Integer status;

    private String failmsg;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

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
        this.batchNo = batchNo;
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
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
        this.username = username;
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
        this.tenderUsername = tenderUsername;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTenderAccountId() {
        return tenderAccountId;
    }

    public void setTenderAccountId(String tenderAccountId) {
        this.tenderAccountId = tenderAccountId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgOrderId() {
        return orgOrderId;
    }

    public void setOrgOrderId(String orgOrderId) {
        this.orgOrderId = orgOrderId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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
        this.checkRetcode = checkRetcode;
    }

    public String getCheckRetmsg() {
        return checkRetmsg;
    }

    public void setCheckRetmsg(String checkRetmsg) {
        this.checkRetmsg = checkRetmsg;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        this.failmsg = failmsg;
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
