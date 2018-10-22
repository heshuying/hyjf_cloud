/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liubin
 * @version BankCreditEndVO, v0.1 2018/7/10 19:30
 */
public class BankCreditEndVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @ApiModelProperty(value = "批次号（批次号当日必须唯一）")
    private String batchNo;
    @ApiModelProperty(value = "本批次交易日期")
    private String txDate;
    @ApiModelProperty(value = "本批次交易时间")
    private String txTime;
    @ApiModelProperty(value = "本批次交易流水号")
    private String seqNo;
    @ApiModelProperty(value = "本批次所有交易笔数")
    private Integer txCounts;
    @ApiModelProperty(value = "融资人用户ID")
    private Integer userId;
    @ApiModelProperty(value = "融资人用户名")
    private String username;
    @ApiModelProperty(value = "投资人用户ID")
    private Integer tenderUserId;
    @ApiModelProperty(value = "投资人用户名")
    private String tenderUsername;
    @ApiModelProperty(value = "融资人电子账号")
    private String accountId;
    @ApiModelProperty(value = "投资人电子账号")
    private String tenderAccountId;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "原始投资订单号")
    private String orgOrderId;
    @ApiModelProperty(value = "标的号")
    private String borrowNid;
    @ApiModelProperty(value = "授权码")
    private String authCode;
    @ApiModelProperty(value = "结束债权类型（1:还款，2:散标债转，3:计划债转）")
    private Integer creditEndType;
    @ApiModelProperty(value = "银行接受结果（0：fail接收失败，1：success接收成功）")
    private Integer received;
    @ApiModelProperty(value = "异步检查响应代码")
    private String checkRetcode;
    @ApiModelProperty(value = "异步检查响应描述")
    private String checkRetmsg;
    @ApiModelProperty(value = "响应代码")
    private String retcode;
    @ApiModelProperty(value = "响应描述")
    private String retmsg;
    @ApiModelProperty(value = "成功笔数")
    private Integer sucCounts;
    @ApiModelProperty(value = "失败笔数")
    private Integer failCounts;
    @ApiModelProperty(value = "银行交易状态（S-成功;F-失败;A-待处理;D-正在处理;C-撤销;）")
    private String state;
    @ApiModelProperty(value = "0初始 1待请求 2请求成功 3请求失败 4校验成功 5业务全部成功  10校验失败 11业务部分成功 12业务失败 ")
    private Integer status;
    @ApiModelProperty(value = "失败原因")
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
