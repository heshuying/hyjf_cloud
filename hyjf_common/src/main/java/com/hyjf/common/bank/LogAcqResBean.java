package com.hyjf.common.bank;

public class LogAcqResBean {

	private String uuid;

	private Integer userId;

	private String feeFrom;

	private String fee;

	private String borrowNid;

	private String client;

	// add by zhangjp 优惠券相关 start
	// 用户优惠券编号
	private String couponGrantId;

	// 更新时间（排他用）
	private Integer couponOldTime;

	// add by zhangjp 优惠券相关 end
	private Integer recordId;
	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * 银行卡Id
	 */
	private Integer cardId;

	/**
	 * 绑卡是从哪个画面跳转过来的 withdraw:提现 recharge:充值
	 * 
	 */
	private String pageFrom;

	/**
	 * 原订单号:原购买债权订单号,债券承接用 liuyang追加
	 */
	private String orgOrderId;
	
	/**
	 * 银行联号 liuyang追加
	 */
	private String payAllianceCode;
	
	/**
	 * 客户端同步回调url
	 */
	private String retUrl;
	/**
	 * 客户端异步回调url
	 */
	private String callBackUrl;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFeeFrom() {
		return feeFrom;
	}

	public void setFeeFrom(String feeFrom) {
		this.feeFrom = feeFrom;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCouponGrantId() {
		return couponGrantId;
	}

	public void setCouponGrantId(String couponGrantId) {
		this.couponGrantId = couponGrantId;
	}

	public Integer getCouponOldTime() {
		return couponOldTime;
	}

	public void setCouponOldTime(Integer couponOldTime) {
		this.couponOldTime = couponOldTime;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public String getPageFrom() {
		return pageFrom;
	}

	public void setPageFrom(String pageFrom) {
		this.pageFrom = pageFrom;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

}
