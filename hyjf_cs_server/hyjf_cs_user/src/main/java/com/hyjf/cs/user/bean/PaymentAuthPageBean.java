package com.hyjf.cs.user.bean;

public class PaymentAuthPageBean extends BaseBean {

    // 请求银行类型
    private String TxCode;
    // 用户id
    private Integer userId;
    
    private String ip;
    
    private String channel;
    // 同步地址
    private String retUrl;
    // 异步地址
    private String notifyUrl;
    // 忘记密码
    private String forgotPwdUrl;
    // 哪个平台发起的
    private String platform;
    
    private String orderId;
    
    private String accountId;
    
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
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }
    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
}
