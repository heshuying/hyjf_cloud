/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version RegisterRequest, v0.1 2018/6/20 15:44
 */
public class RegisterRequest {


    @ApiModelProperty(value = "手机号")
    private String mobilephone;
    @ApiModelProperty(value = "验证码")
    private String smsCode;
    @ApiModelProperty(value = "推荐人")
    private String reffer;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "注册渠道")
    private String utmId;
    @ApiModelProperty(value = "机构编号")
    private String instCode;
    @ApiModelProperty(value = "注册平台")
    private String platform;

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getReffer() {
        return reffer;
    }

    public void setReffer(String reffer) {
        this.reffer = reffer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
