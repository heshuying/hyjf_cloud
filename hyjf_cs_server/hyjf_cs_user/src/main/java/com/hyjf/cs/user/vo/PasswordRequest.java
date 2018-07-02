/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangqingqing
 * @version passwordRequest, v0.1 2018/6/26 16:33
 */
public class PasswordRequest extends AppBaseRequest{

    @ApiModelProperty(value = "验证码（找回密码接口）")
    private String verificationCode;

    @ApiModelProperty(value = "新密码 app")
    private String newPassword;

    @ApiModelProperty(value = "旧密码 app(修改密码)")
    private String oldPassword;

    @ApiModelProperty(value = "确认密码 web")
    private String pwSure;

    @ApiModelProperty(value = " app")
    private String client;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPwSure() {
        return pwSure;
    }

    public void setPwSure(String pwSure) {
        this.pwSure = pwSure;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
