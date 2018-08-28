/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: AdminBankCardExceptionCustomize, v0.1 2018/8/14 15:06
 */
public class AdminBankCardExceptionCustomize implements Serializable {
    private static final long serialVersionUID = 1L;
    // 用戶id
    public String userId;
    // 用戶名
    public String username;
    /** 用户真实姓名 */
    private String truename;
    /** 身份证号 */
    private String idcard;
    // 银行账户
    public String account;
    // 所属银行代码
    private String bankcode;
    // 银行
    public String bank;
    // 银行卡是否是默认提现卡
    public String cardType;
    // 默认卡
    private String isdefault;
    // 银行卡属性
    private String bankShuxing;
    // 添加时间
    private String addtime;
    //用户手机号
    private String mobile;
    /** 银行预留手机号 */
    private String bankMobile;
    /**专门添加为了调用汇付接口,从而核对银行卡信息*/
    //汇付客户号
    public String customerAccount;
    // 银行卡是否是快捷支付卡
    public String cardProperty;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getBankShuxing() {
        return bankShuxing;
    }

    public void setBankShuxing(String bankShuxing) {
        this.bankShuxing = bankShuxing;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getCardProperty() {
        return cardProperty;
    }

    public void setCardProperty(String cardProperty) {
        this.cardProperty = cardProperty;
    }
}
