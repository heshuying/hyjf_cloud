/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: ApiUserRechargeRequestBean, v0.1 2018/8/28 10:32
 */
@ApiModel(value = "api端-用户充值请求参数")
public class ApiUserRechargeRequestBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 8988592724638111679L;

    @ApiModelProperty(value = "银行卡预留手机号")
    private String mobile;

    @ApiModelProperty(value = "用户电子账户号")
    private String accountId;

    @ApiModelProperty(value = "银行卡号")
    private String cardNo;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "充值金额")
    private String account;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
