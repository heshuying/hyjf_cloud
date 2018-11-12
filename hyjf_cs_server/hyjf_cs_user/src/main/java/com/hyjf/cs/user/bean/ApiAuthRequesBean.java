package com.hyjf.cs.user.bean;

public class ApiAuthRequesBean extends BaseBean   {


    // 返回信息
    private  String accoutnId;
    private  String forgotPwdUrl;
    private  String notifyUrl;

    private  String authType;

    public String getAccoutnId() {
        return accoutnId;
    }

    public void setAccoutnId(String accoutnId) {
        this.accoutnId = accoutnId;
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
