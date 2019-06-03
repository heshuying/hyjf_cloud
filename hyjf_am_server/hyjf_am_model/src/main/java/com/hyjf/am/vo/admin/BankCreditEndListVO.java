package com.hyjf.am.vo.admin;

import java.util.Date;

/**
 * 结束债权列表VO
 * @author hesy
 */
public class BankCreditEndListVO {
    private Integer id;

    /**
     * 批次号（批次号当日必须唯一）
     *
     * @mbggenerated
     */
    private String batchNo;

    /**
     * 本批次交易日期
     *
     * @mbggenerated
     */
    private String txDate;

    /**
     * 本批次交易时间
     *
     * @mbggenerated
     */
    private String txTime;

    /**
     * 本批次交易流水号
     *
     * @mbggenerated
     */
    private String seqNo;

    /**
     * 本批次所有交易笔数
     *
     * @mbggenerated
     */
    private Integer txCounts;

    /**
     * 融资人用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 融资人用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 投资人用户ID
     *
     * @mbggenerated
     */
    private Integer tenderUserId;

    /**
     * 投资人用户名
     *
     * @mbggenerated
     */
    private String tenderUsername;

    /**
     * 融资人电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 投资人电子账号
     *
     * @mbggenerated
     */
    private String tenderAccountId;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 原始投资订单号
     *
     * @mbggenerated
     */
    private String orgOrderId;

    /**
     * 标的号
     *
     * @mbggenerated
     */
    private String borrowNid;

    private String authCode;

    /**
     * 结束债权类型（1:还款，2:散标债转，3:计划债转）
     *
     * @mbggenerated
     */
    private Integer creditEndType;

    /**
     * 银行接受结果（0：fail接收失败，1：success接收成功）
     *
     * @mbggenerated
     */
    private Integer received;

    /**
     * 异步检查响应代码
     *
     * @mbggenerated
     */
    private String checkRetcode;

    /**
     * 异步检查响应描述
     *
     * @mbggenerated
     */
    private String checkRetmsg;

    /**
     * 响应代码
     *
     * @mbggenerated
     */
    private String retcode;

    /**
     * 响应描述
     *
     * @mbggenerated
     */
    private String retmsg;

    /**
     * 成功笔数
     *
     * @mbggenerated
     */
    private Integer sucCounts;

    /**
     * 失败笔数
     *
     * @mbggenerated
     */
    private Integer failCounts;

    /**
     * 银行交易状态（S-成功;F-失败;A-待处理;D-正在处理;C-撤销;）
     *
     * @mbggenerated
     */
    private String state;

    /**
     * 0初始 1待请求 2请求成功 3请求失败 4校验成功 5业务全部成功  10校验失败 11业务部分成功 12业务失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 失败描述
     *
     * @mbggenerated
     */
    private String failmsg;

    /**
     * 添加人
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新人
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /** 债权结束状态描述*/
    private String stateDesc;

    /** 提交时间*/
    private String commitTime;

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

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }
}
