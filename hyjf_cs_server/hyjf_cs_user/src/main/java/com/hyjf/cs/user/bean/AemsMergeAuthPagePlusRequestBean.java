/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * AEMS系统:多合一授权请求Bean
 *
 * @author liuyang
 * @version AemsMergeAuthPagePlusRequestBean, v0.1 2018/12/13 14:27
 */
public class AemsMergeAuthPagePlusRequestBean extends BaseBean {

    @ApiModelProperty(value = "电子账户号")
    private  String accountId;

    @ApiModelProperty(value = "忘记密码URL")
    private  String forgotPwdUrl;

    @ApiModelProperty(value = "异步回调URL")
    private  String notifyUrl;

    @ApiModelProperty(value = "授权类型")
    private  String authType;

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }

    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

}
