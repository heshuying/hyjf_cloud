/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangjun
 * @version SrchRechargeInfoBean, v0.1 2018/6/14 14:35
 */
public class SrchRechargeInfoBean implements Serializable {
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
     * 1.用户名
     */
    private String username;
    /**
     * 2.手机号
     */
    private String mobile;
    /**
     * 3.流水号
     */
    private String seqNo;
    /**
     * 4.资金托管平台
     */
    private String isBank;
    /**
     * 5.订单号
     */
    private String nid;
    /**
     * 6.充值类型
     */
    private String gateType;
    /**
     * 7.充值银行
     */
    private String bankName;
    /**
     * 8.充值金额
     */
    private BigDecimal money;
    /**
     * 9.手续费
     */
    private BigDecimal fee;
    /**
     * 10.垫付手续费
     */
    private BigDecimal dianfuFee;
    /**
     * 11.实际到账余额
     */
    private BigDecimal balance;
    /**
     * 12.充值状态
     */
    private String status;
    /**
     * 13.充值平台 0pc 1app
     */
    private String client;
    /**
     * 14.充值时间
     */
    private String createTime;
    /**
     *  15.失败原因
     */
    private String message;

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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public BigDecimal getDianfuFee() {
        return dianfuFee;
    }

    public void setDianfuFee(BigDecimal dianfuFee) {
        this.dianfuFee = dianfuFee;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
