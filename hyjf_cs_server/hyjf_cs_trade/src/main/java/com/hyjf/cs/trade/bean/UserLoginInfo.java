package com.hyjf.cs.trade.bean;


/**
 * 用户验证
 */

public class UserLoginInfo {


    // 是否登录
    private Boolean isLogined;

    // 是否开户
    private Boolean isOpened;
    // 是否设置交易密码
    private Boolean isSetPassword;
    // 是否允许使用
    private Boolean isAllowed;
    // 是否完成风险测评
    private String isRiskTested;
    // 是否投资授权
    private Boolean isAutoInves;
    // 是否债转授权
    private Boolean isAutoTransfer;
    // 角色是否允许投资
    private Boolean isAllowedTender;

    /**
     * 缴费授权状态
     */
    private Integer paymentAuthStatus;
    // 缴费授权开关
    private Integer paymentAuthOn;
    // 自动投资授权开关
    private Integer invesAuthOn;
    // 自动债转授权开关
    private Integer creditAuthOn;
    //角色认证是否打开,true认证,false不认证
    private Boolean isCheckUserRole;

    public Boolean getLogined() {
        return isLogined;
    }

    public void setLogined(Boolean logined) {
        isLogined = logined;
    }

    public Boolean getOpened() {
        return isOpened;
    }

    public void setOpened(Boolean opened) {
        isOpened = opened;
    }

    public Boolean getSetPassword() {
        return isSetPassword;
    }

    public void setSetPassword(Boolean setPassword) {
        isSetPassword = setPassword;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }

    public String getRiskTested() {
        return isRiskTested;
    }

    public void setRiskTested(String riskTested) {
        isRiskTested = riskTested;
    }

    public Boolean getAutoInves() {
        return isAutoInves;
    }

    public void setAutoInves(Boolean autoInves) {
        isAutoInves = autoInves;
    }

    public Boolean getAutoTransfer() {
        return isAutoTransfer;
    }

    public void setAutoTransfer(Boolean autoTransfer) {
        isAutoTransfer = autoTransfer;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public Boolean getIsAllowedTender() {
        return isAllowedTender;
    }

    public void setIsAllowedTender(Boolean isAllowedTender) {
        this.isAllowedTender = isAllowedTender;
    }

    public Integer getPaymentAuthOn() {
        return paymentAuthOn;
    }

    public void setPaymentAuthOn(Integer paymentAuthOn) {
        this.paymentAuthOn = paymentAuthOn;
    }

    public Integer getInvesAuthOn() {
        return invesAuthOn;
    }

    public void setInvesAuthOn(Integer invesAuthOn) {
        this.invesAuthOn = invesAuthOn;
    }

    public Integer getCreditAuthOn() {
        return creditAuthOn;
    }

    public void setCreditAuthOn(Integer creditAuthOn) {
        this.creditAuthOn = creditAuthOn;
    }

    public Boolean getIsCheckUserRole() {
        return isCheckUserRole;
    }

    public void setIsCheckUserRole(Boolean checkUserRole) {
        isCheckUserRole = checkUserRole;
    }
}
