package com.hyjf.cs.user.bean;


public class OpenAccountPageBean {

    /**
     * 请求银行类型
     */
    private String txCode;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 性别
     */
    private  String gender;

    /**
     * 身份属性  1：出借角色 2：借款角色 3：代偿角色
     */
    private  String identity;

    /**
     * 用户id
     */
    private  Integer userId;
    
    private  String ip;
    
    private  String channel;

    /**
     *  账户用途00000-普通账户
     *  10000-红包账户（只能有一个）
     *  01000-手续费账户（只能有一个）
     *  00100-担保账户
     */
    private  String acctUse;

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
     * 银行卡
     */
    private  String cardNo;

    /**
     * 跳转到哪一个前端
     */
    private  String clientHeader;

    private  String successfulUrl;

    /**
     * 温金投平台  ClientConstants.WJT_PC_CLIENT   ClientConstants.WJT_WEI_CLIENT
     */
    private String wjtClient;

    
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
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
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
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
    public String getAcctUse() {
        return acctUse;
    }
    public void setAcctUse(String acctUse) {
        this.acctUse = acctUse;
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
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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


    public String getWjtClient() {
        return wjtClient;
    }

    public void setWjtClient(String wjtClient) {
        this.wjtClient = wjtClient;
    }
}
