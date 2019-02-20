/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AccountRechargeVO, v0.1 2018/7/9 10:56
 */
@ApiModel(value = "平台转账返回值参数")
public class AccountRechargeVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "订单号")
    private String nid;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "电子账号")
    private String accountId;
    @ApiModelProperty(value = "交易日期")
    private Integer txDate;
    @ApiModelProperty(value = "交易日期字符串")
    private String txDateStr;
    @ApiModelProperty(value = "交易时间")
    private Integer txTime;
    @ApiModelProperty(value = "交易时间字符串")
    private String txTimeStr;
    @ApiModelProperty(value = "交易流水号")
    private Integer seqNo;
    @ApiModelProperty(value = "银行流水号(交易日期+交易时间+交易流水号)")
    private String bankSeqNo;
    @ApiModelProperty(value = "资金托管平台(0:汇付,1:江西银行)")
    private Integer isBank;
    @ApiModelProperty(value = "充值状态:0:充值中,1:充值失败,2:充值成功")
    private Integer status;
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    @ApiModelProperty(value = "费用")
    private BigDecimal fee;
    @ApiModelProperty(value = "实际到账余额")
    private BigDecimal balance;
    @ApiModelProperty(value = "所属银行")
    private String payment;
    @ApiModelProperty(value = "网关类型：QP快捷支付;B2C网关充值;B2B企业充值")
    private String gateType;
    @ApiModelProperty(value = "类型.1网上充值.0线下充值")
    private Integer type;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "操作人")
    private String operator;
    @ApiModelProperty(value = "ip")
    private String addIp;
    @ApiModelProperty(value = "平台0pc 1WX 2AND 3IOS 4other")
    private Integer client;
    @ApiModelProperty(value = "银行卡号，导出数据关联时用到")
    private String cardid;
    @ApiModelProperty(value = "消息记录")
    private String message;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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
        this.nid = nid;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getTxTimeStr() {
        return txTimeStr;
    }

    public void setTxTimeStr(String txTimeStr) {
        this.txTimeStr = txTimeStr;
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
        this.bankSeqNo = bankSeqNo;
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTxDateStr() {
        return txDateStr;
    }

    public void setTxDateStr(String txDateStr) {
        this.txDateStr = txDateStr;
    }
}
