/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: RegistLandingPageCommitRequestBean, v0.1 2018/8/2 11:40
 */
@ApiModel(value = "着陆页提交注册接口参数")
public class RegistLandingPageCommitRequestBean extends BaseRequestBean {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String verificationCode;
    @ApiModelProperty(value = "推荐人id")
    private String refferUserId;
    @ApiModelProperty(value = "推广链接id")
    private String utmId;
    @ApiModelProperty(value = "财富端用户id")
    private String customerId;
    @ApiModelProperty(value = "暂时没发现哪里用")
    private String utmSource;
    @ApiModelProperty(value = "验证方式")
    private String newRegVerifyCode;
    @ApiModelProperty(value = "验证码")
    private String verificationType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getRefferUserId() {
        return refferUserId;
    }

    public void setRefferUserId(String refferUserId) {
        this.refferUserId = refferUserId;
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

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getNewRegVerifyCode() {
        return newRegVerifyCode;
    }

    public void setNewRegVerifyCode(String newRegVerifyCode) {
        this.newRegVerifyCode = newRegVerifyCode;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }
}
