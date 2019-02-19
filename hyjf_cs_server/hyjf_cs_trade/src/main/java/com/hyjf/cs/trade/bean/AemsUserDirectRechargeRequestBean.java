package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version AemsUserDirectRechargeRequestBean, v0.1 2018/12/6 18:03
 * @Author: Zha Daojian
 */
public class AemsUserDirectRechargeRequestBean extends BaseBean {

    @ApiModelProperty(value = "身份证")
    private String idNo;
    @ApiModelProperty(value = "用户电子账户号")
    private String accountId;

    @ApiModelProperty(value = "充值金额")
    private String txAmount;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "交易渠道")
    private String  channel;
    @ApiModelProperty(value = "银行卡卡号")
    private String  cardNo;
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getTxAmount() {
        return txAmount;
    }
    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
