package com.hyjf.cs.borrow.bean;

import com.hyjf.pay.lib.bank.bean.BankCallBean;

public class UserDirectRechargeBean  {

    // 请求银行类型
    private String TxCode;
    // 手机号
    private String mobile;
    // 姓名
    private String name;
    // 身份证号
    private String idNo;
    // 银行卡号
    private String cardNo;
    // 性别
    private  String gender;
    // 身份属性  1：出借角色 2：借款角色 3：代偿角色
    private  String identity;
    // 用户id
    private  Integer userId;
    
    private  String ip;
    
    private  String channel;
    
    private  String accountId;
    // 同步地址
    private  String retUrl;
    // 异步地址
    private  String notifyUrl;
    // 忘记密码地址
    private  String forgotPwdUrl;
    // 成功后跳转的链接
    private  String successfulUrl;
    
    // 哪个平台发起的
    private  String platform;
    
    private  String txAmount;
    
    BankCallBean bean;
    
    private  String userName;
    
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getTxCode() {
        return TxCode;
    }
    public void setTxCode(String txCode) {
        TxCode = txCode;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getRetUrl() {
        return retUrl;
    }
    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getTxAmount() {
        return txAmount;
    }
    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }
    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }
    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public BankCallBean getBean() {
        return bean;
    }
    public void setBean(BankCallBean bean) {
        this.bean = bean;
    }
    public String getSuccessfulUrl() {
        return successfulUrl;
    }
    public void setSuccessfulUrl(String successfulUrl) {
        this.successfulUrl = successfulUrl;
    }
}
