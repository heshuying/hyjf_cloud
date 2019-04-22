/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version AppRegisterRequest, v0.1 2018/6/23 11:09
 */
public class RegisterRequest extends AppBaseRequest {

    @ApiModelProperty(value = "手机号web.app.weChat")
    private String mobile ;

    @ApiModelProperty(value = "验证码web.app.weChat")
    private String verificationCode;

    @ApiModelProperty(value = "登录密码web.app.weChat")
    private String password ;

    @ApiModelProperty(value = "推荐人web.app.weChat")
    private String reffer;

    @ApiModelProperty(value = "机构编号web")
    private String instCode;
    @ApiModelProperty(value = "注册渠道web")
    private String utmId;

    // wbs add by cuigq
    @ApiModelProperty(value = "财富端客户ID")
    private String customerId;

    @ApiModelProperty(value = "神策预置属性")
    private String presetProps;

    // 合规改造 add by huanghui 20181220 start
    @ApiModelProperty(value = "当前注册用的类型:0:普通用户;1:企业用户;")
    private Integer userType;
    // 合规改造 add by huanghui 20181220 end

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
