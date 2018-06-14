/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangjun
 * @version SrchCapitalInfoBean, v0.1 2018/6/5 19:32
 */
public class SrchCapitalInfoBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5903470202035400345L;
    /**
     * 请求时间戳(暂时)
     */
    private String timestamp;
    /**
     * md5校验码(暂时)
     */
    private String sign;
    //以下为需求定义字段
    /**
     * 序号（自增的，暂时）
     */
    private String creditId;
    /**
     * 1.用户名
     */
    private String username;
    /**
     * 2.手机号
     */
    private String mobile;
    /**
     * 3.推荐人
     */
    private String referrerName;
    /**
     * 4.资金托管平台 (账户类型是否银行存管)
     */
    private String isBank;
    /**
     * 5.流水号
     */
    private String seqNo;
    /**
     * 6.订单号
     */
    private String nid;
    /**
     * 7.收支类型(操作类型)
     */
    private String type;
    /**
     * 8.交易类型
     */
    private String tradeType;
    /**
     * 9.操作金额
     */
    private BigDecimal amount;
    /**
     * 10.银行可用金额(银行存管余额)
     */
    private BigDecimal bankBalance;
    /**
     * 11.银行存管冻结金额
     */
    private BigDecimal bankFrost;
    /**
     * 12.汇付可用金额(可用余额)
     */
    private BigDecimal balance;
    /**
     * 13.汇付冻结金额(冻结金额)
     */
    private BigDecimal frost;
    /**
     * 14.交易状态
     */
    private String tradeStatus;
    /**
     * 15.对账状态
     */
    private String checkStatus;
    /**
     * 16.备注说明
     */
    private String remark;
    /**
     * 17.交易时间
     */
    private String createTime;
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getCreditId() {
        return creditId;
    }
    public void setCreditId(String creditId) {
        this.creditId = creditId;
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
    public String getReferrerName() {
        return referrerName;
    }
    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }
    public String getIsBank() {
        return isBank;
    }
    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
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
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getFrost() {
        return frost;
    }
    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }
    public String getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    public String getCheckStatus() {
        return checkStatus;
    }
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
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
}
