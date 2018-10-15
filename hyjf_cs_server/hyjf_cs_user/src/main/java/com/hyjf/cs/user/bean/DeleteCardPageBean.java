package com.hyjf.cs.user.bean;

public class DeleteCardPageBean {
	//交易代码
	private String txCode;
	//电子账号
	private String accountId;
	//姓名
	private String name;
	//证件类型
	private String idType;
	//证件号码
	private String idNo;
	//绑定卡号
	private String cardNo;
	//手机号码
	private String mobile;
	//前台跳转链接
	private String retUrl;
	//忘记密码跳转
	private String forgotPwdUrl;
	//后台验证订单有效性连接
	private String verifyOrderNoUrl;
	//交易成功跳转链接
	private String successfulUrl;
	//交易成功跳转链接
	private String notifyUrl;

	//
	private String channel;
	//用户id
	private Integer userId;
	private  String ip;
	// 哪个平台发起的
	private  String platform;

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getForgotPwdUrl() {
		return forgotPwdUrl;
	}

	public void setForgotPwdUrl(String forgotPwdUrl) {
		this.forgotPwdUrl = forgotPwdUrl;
	}

	public String getVerifyOrderNoUrl() {
		return verifyOrderNoUrl;
	}

	public void setVerifyOrderNoUrl(String verifyOrderNoUrl) {
		this.verifyOrderNoUrl = verifyOrderNoUrl;
	}

	public String getSuccessfulUrl() {
		return successfulUrl;
	}

	public void setSuccessfulUrl(String successfulUrl) {
		this.successfulUrl = successfulUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
