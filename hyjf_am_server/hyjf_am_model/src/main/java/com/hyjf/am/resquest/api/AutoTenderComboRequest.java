/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.api;

import java.io.Serializable;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

/**
 * @author libin
 * 第三方投资散标请求组合实体
 * @version AutoTenderComboRequest.java, v0.1 2018年8月27日 上午9:17:44
 */
public class AutoTenderComboRequest extends BasePage implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 标的号
	 */
	private String borrowNid;
	
	/**
	 * 生成订单号
	 */
	private String generateOrderId;
	
	/**
	 * userId
	 */
	private Integer userId;
	
	/**
	 * 投资金额
	 */
	private String account;
	
	/**
	 * ipAddress
	 */
	private String ipAddress;
	
	/**
	 * couponGrantId
	 */
	private String couponGrantId;
	
	/**
	 * tenderUserName
	 */
	private String tenderUserName;
	
	/**
	 * borrow
	 */
	private BorrowAndInfoVO borrow;
	
	/*以下是BankCallBean中涉及到的字段*/
	/** 操作用户ip */
	private String logIp;
	
	/** 用户操作平台 */
	private int logClient;
	
	/** 电子账号 */
	private String accountId;
	
	/** 操作用户userId */
	private String logUserId;
	
	/** 交易金额 */
	private String txAmount;
	
	/** 订单号 */
	private String orderId;
	
	/** 订单日期 */
	private String logOrderDate;
	
	/** 响应代码 */
	private String retCode;
	
	/** 授权码:投资人投标成功的授权号 */
	private String authCode;
	
	/** 交易日期 */
	public String txDate;
	
	/** 交易时间 */
	public String txTime;
	
	/** 交易流水号 */
	public String seqNo;

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getGenerateOrderId() {
		return generateOrderId;
	}

	public void setGenerateOrderId(String generateOrderId) {
		this.generateOrderId = generateOrderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCouponGrantId() {
		return couponGrantId;
	}

	public void setCouponGrantId(String couponGrantId) {
		this.couponGrantId = couponGrantId;
	}

	public String getTenderUserName() {
		return tenderUserName;
	}

	public void setTenderUserName(String tenderUserName) {
		this.tenderUserName = tenderUserName;
	}

	public BorrowAndInfoVO getBorrow() {
		return borrow;
	}

	public void setBorrow(BorrowAndInfoVO borrow) {
		this.borrow = borrow;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public int getLogClient() {
		return logClient;
	}

	public void setLogClient(int logClient) {
		this.logClient = logClient;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}

	public String getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLogOrderDate() {
		return logOrderDate;
	}

	public void setLogOrderDate(String logOrderDate) {
		this.logOrderDate = logOrderDate;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	/*以上是BankCallBean中涉及到的字段*/
}
