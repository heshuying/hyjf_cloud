/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyBean, v0.1 2019/5/8 15:20
 */
public class BankMobileModifyBean {

    /**
     * 存关子账户
     */
    private String accountId;

    private String mobile;

    /**
     * 用户id
     */
    private  Integer userId;

    private  String ip;

    private  String channel;

    /**
     * 同步地址
     */
    private  String retUrl;

    /**
     * 异步地址
     */
    private  String notifyUrl;

    /**
     * 哪个平台发起的
     */
    private  String platform;

    private  String orderId;

    /**
     * 商户名称
     */
    private  String coinstName;

    /**
     * 跳转到哪一个前端
     */
    private  String clientHeader;

    private  String successfulUrl;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCoinstName() {
        return coinstName;
    }

    public void setCoinstName(String coinstName) {
        this.coinstName = coinstName;
    }

    public String getClientHeader() {
        return clientHeader;
    }

    public void setClientHeader(String clientHeader) {
        this.clientHeader = clientHeader;
    }

    public String getSuccessfulUrl() {
        return successfulUrl;
    }

    public void setSuccessfulUrl(String successfulUrl) {
        this.successfulUrl = successfulUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
