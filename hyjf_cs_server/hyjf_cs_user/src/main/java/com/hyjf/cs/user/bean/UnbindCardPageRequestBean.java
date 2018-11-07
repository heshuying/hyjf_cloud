package com.hyjf.cs.user.bean;

public class UnbindCardPageRequestBean extends BaseBean{
    private String cardNo;

    private String mobile;
    
    private String code;

    private  String forgotPwdUrl;
    private  String notifyUrl;
    
    private String lastSrvAuthCode;
    //解绑平台
    private String platform;
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastSrvAuthCode() {
        return lastSrvAuthCode;
    }

    public void setLastSrvAuthCode(String lastSrvAuthCode) {
        this.lastSrvAuthCode = lastSrvAuthCode;
    }

    @Override
    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }

    @Override
    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String getPlatform() {
        return platform;
    }

    @Override
    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
