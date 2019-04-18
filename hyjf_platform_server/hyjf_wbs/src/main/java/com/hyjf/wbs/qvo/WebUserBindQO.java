/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

/**
 * web 绑定用户请求封装
 * @author cui
 * @version WebUserBindQO, v0.1 2019/4/18 16:18
 */
public class WebUserBindQO {

    @ApiModelProperty(value = "登录用户名")
    private String loginUserName;

    @ApiModelProperty(value = "密码")
    private String loginPassword;

    @ApiModelProperty(value = "同意协议")
    private Boolean readAgreement;

    @ApiModelProperty(value = "财富端id")
    private String  utmId;

    @ApiModelProperty(value = "财富端客户id")
    private String customerId;


    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Boolean getReadAgreement() {
        return readAgreement;
    }

    public void setReadAgreement(Boolean readAgreement) {
        this.readAgreement = readAgreement;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
