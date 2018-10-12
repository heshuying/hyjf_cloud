package com.hyjf.cs.user.bean;


/**
 *
 *
 * Created by yaoyong on 2017/11/29.
 */
public class OpenAccountPlusResult extends BaseResultBean {

    //发送短信的orderId
    private String orderId;
    //是否开户
    private String isOpenAccount;
    // 电子账户号
    private String accountId;
    //银行卡联行号
    private String payAllianceCode;
    //设置交易密码状态
    private String isSetPassword;
    //自动投标授权状态
    private String autoInvesStatus;
    //真实姓名
    private String trueName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public String getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(String isSetPassword) {
        this.isSetPassword = isSetPassword;
    }

    public String getAutoInvesStatus() {
        return autoInvesStatus;
    }

    public void setAutoInvesStatus(String autoInvesStatus) {
        this.autoInvesStatus = autoInvesStatus;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
