/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: UserDirectRechargeRequestBean, v0.1 2018/8/28 19:25
 */
@ApiModel(value = "用户充值请求参数(页面调用)")
public class UserDirectRechargeRequestBean extends BaseBean {
    @ApiModelProperty(value = "身份证号码")
    private String idNo;
    @ApiModelProperty(value = "用户电子账户号")
    private String accountId;
    @ApiModelProperty(value = "充值金额")
    private String txAmount;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "渠道")
    private String channel;
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
