package com.hyjf.cs.user.bean;

public class BindCardPageBean extends BaseBean {

    // 交易代码
    private String TxCode;
    // 证件类型
    private String idType;
    // 证件号码
    private String idNo;

    // 用户id
    private  Integer userId;
    // 数据来源
    private  String platform;
    // 姓名
    private  String name;
    // 电子账号
    private  String accountId;
    // 客户IP
    private  String userIP;
    
    private  String channel;
    // 同步地址
    private  String retUrl;
    // 异步地址
    private  String notifyUrl;
    
    // 成功回调
    private  String successfulUrl;
    // 忘记密码
    private  String forgetPassworedUrl;
    
	public String getTxCode() {
		return TxCode;
	}
	public void setTxCode(String txCode) {
		TxCode = txCode;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
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
	public String getSuccessfulUrl() {
		return successfulUrl;
	}
	public void setSuccessfulUrl(String successfulUrl) {
		this.successfulUrl = successfulUrl;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getForgetPassworedUrl() {
		return forgetPassworedUrl;
	}
	public void setForgetPassworedUrl(String forgetPassworedUrl) {
		this.forgetPassworedUrl = forgetPassworedUrl;
	}

   
}
