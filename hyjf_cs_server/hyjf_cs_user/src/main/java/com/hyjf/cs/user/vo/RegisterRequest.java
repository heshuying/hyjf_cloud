/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version AppRegisterRequest, v0.1 2018/6/23 11:09
 */
public class RegisterRequest {

    @ApiModelProperty(value = "版本号app")
    private String version;

    @ApiModelProperty(value = "网络状态app")
    private String netStatus;

    @ApiModelProperty(value = "平台web.app.weChat.api")
    private String platform;

    @ApiModelProperty(value = "随机字符串app")
    private String randomString ;

    @ApiModelProperty(value = "Orderapp")
    private String order;

    @ApiModelProperty(value = "手机号web.app.weChat.api")
    private String mobile ;

    @ApiModelProperty(value = "验证码web.app.weChat")
    private String smsCode;

    @ApiModelProperty(value = "登录密码web.app.weChat")
    private String password ;

    @ApiModelProperty(value = "推荐人web.app.weChat.api")
    private String reffer;

    @ApiModelProperty(value = "机构编号api")
    private String instCode;
    @ApiModelProperty(value = "注册渠道web.api")
    private String utmId;

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReffer() {
        return reffer;
    }

    public void setReffer(String reffer) {
        this.reffer = reffer;
    }
}
