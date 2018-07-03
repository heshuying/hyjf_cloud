/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version SmsRequest, v0.1 2018/7/2 15:10
 */
public class SmsRequest extends AppBaseRequest{

    @ApiModelProperty(value = "验证方式")
   private String verificationType;

    @ApiModelProperty(value = "验证码")
    private String verificationCode;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
