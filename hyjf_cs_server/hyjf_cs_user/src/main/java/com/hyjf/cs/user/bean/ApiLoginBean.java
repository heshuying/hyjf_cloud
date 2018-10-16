/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author: sunpeikai
 * @version: ApiLoginBean, v0.1 2018/10/16 10:48
 */
public class ApiLoginBean extends BaseBean {

    private String loginUserName;

    private String loginPassword;

    /** 同意协议 */
    private boolean readAgreement;

    // 神策预置属性
    private String presetProps;

    public boolean getReadAgreement() {
        return readAgreement;
    }

    public void setReadAgreement(boolean readAgreement) {
        this.readAgreement = readAgreement;
    }

    private String captcha;
    // add by zhangjp 支持登录完成后跳转回原页面 20161014 start
    // 原页面的回调路径
    private String retUrl;
    // add by zhangjp 支持登录完成后跳转回原页面 20161014 end

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

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }
}
