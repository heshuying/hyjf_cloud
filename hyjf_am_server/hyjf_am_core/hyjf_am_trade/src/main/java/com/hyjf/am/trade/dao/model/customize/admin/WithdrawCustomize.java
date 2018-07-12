package com.hyjf.am.trade.dao.model.customize.admin;

import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;

import java.io.Serializable;

public class WithdrawCustomize extends AccountWithdraw implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String username;
	/** 订单号，提现银行，提交时间，提现平台，处理状态 */
	private String ordid;
	/** 用户属性 */
	private String userProperty;
	/** 提交时间 */
	private String addtimeStr;
	/** 提现平台 */
	private String clientStr;
	/** 处理状态 */
	private String statusStr;
	/** IP地址 */
	private String ip;

	/**
	 * 用户属性（当前）
	 */
	private String userAttribute;

	/**
	 * 用户所属一级分部（当前）
	 */
	private String userRegionName;

	/**
	 * 用户所属二级分部（当前）
	 */
	private String userBranchName;

	/**
	 * 用户所属团队（当前）
	 */
	private String userDepartmentName;

	/**
	 * 推荐人用户名（当前）
	 */
	private String referrerName;

	/**
	 * 推荐人姓名（当前）
	 */
	private String referrerUserId;

	/**
	 * 推荐人姓名（当前）
	 */
	private String referrerTrueName;

	/**
	 * 推荐人所属一级分部（当前）
	 */
	private String referrerRegionName;

	/**
	 * 推荐人所属二级分部（当前）
	 */
	private String referrerBranchName;

	/**
	 * 推荐人所属团队（当前）
	 */
	private String referrerDepartmentName;

	/**
	 * 失败原因
	 */
	private String reason;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 用户角色
	 */
	private String roleid;

	// 查询用变量
	/** 用户名 */
	private String usernameSrch;
	/** 订单号 */
	private String ordidSrch;
	/** 提现平台 */
	private String clientSrch;
	/** 提现状态 */
	private String statusSrch;
	/** 添加时间(开始) */
	private String addtimeStartSrch;
	/** 添加时间(结束) */
	private String addtimeEndSrch;
	/** 提现异常状态 */
	private String statusExceptionSrch;
	/** 翻页开始 */
	protected int limitStart = -1;
	/** 翻页结束 */
	protected int limitEnd = -1;

	/** 提现来源 */
	private Integer bankFlag;
	/** 银行电子账号 */
	private String accountId;
	/** 银行流水号 */
	private Integer seqNo;
	
	private String txDateS;
	
	private String txTimeS;

	/** 提现来源 */
	private String bankFlagSrch;
	/** 银行电子账号 */
	private String accountIdSrch;
	/** 银行流水号 */
	private String seqNoSrch;
	/** 银行流水号 */
	private String bankSeqNoSrch;

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public String getUsernameSrch() {
		return usernameSrch;
	}

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrdid() {
		return ordid;
	}

	public void setOrdid(String ordid) {
		this.ordid = ordid;
	}

	public String getAddtimeStr() {
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getClientStr() {
		return clientStr;
	}

	public void setClientStr(String clientStr) {
		this.clientStr = clientStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getAddtimeStartSrch() {
		return addtimeStartSrch;
	}

	public void setAddtimeStartSrch(String addtimeStartSrch) {
		this.addtimeStartSrch = addtimeStartSrch;
	}

	public String getAddtimeEndSrch() {
		return addtimeEndSrch;
	}

	public void setAddtimeEndSrch(String addtimeEndSrch) {
		this.addtimeEndSrch = addtimeEndSrch;
	}

	public String getOrdidSrch() {
		return ordidSrch;
	}

	public void setOrdidSrch(String ordidSrch) {
		this.ordidSrch = ordidSrch;
	}

	public String getClientSrch() {
		return clientSrch;
	}

	public void setClientSrch(String clientSrch) {
		this.clientSrch = clientSrch;
	}

	public String getStatusSrch() {
		return statusSrch;
	}

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatusExceptionSrch() {
		return statusExceptionSrch;
	}

	public void setStatusExceptionSrch(String statusExceptionSrch) {
		this.statusExceptionSrch = statusExceptionSrch;
	}

	public String getUserAttribute() {
		return userAttribute;
	}

	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	public String getUserRegionName() {
		return userRegionName;
	}

	public void setUserRegionName(String userRegionName) {
		this.userRegionName = userRegionName;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getUserDepartmentName() {
		return userDepartmentName;
	}

	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerUserId() {
		return referrerUserId;
	}

	public void setReferrerUserId(String referrerUserId) {
		this.referrerUserId = referrerUserId;
	}

	public String getReferrerTrueName() {
		return referrerTrueName;
	}

	public void setReferrerTrueName(String referrerTrueName) {
		this.referrerTrueName = referrerTrueName;
	}

	public String getReferrerRegionName() {
		return referrerRegionName;
	}

	public void setReferrerRegionName(String referrerRegionName) {
		this.referrerRegionName = referrerRegionName;
	}

	public String getReferrerBranchName() {
		return referrerBranchName;
	}

	public void setReferrerBranchName(String referrerBranchName) {
		this.referrerBranchName = referrerBranchName;
	}

	public String getReferrerDepartmentName() {
		return referrerDepartmentName;
	}

	public void setReferrerDepartmentName(String referrerDepartmentName) {
		this.referrerDepartmentName = referrerDepartmentName;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getBankFlagSrch() {
		return bankFlagSrch;
	}

	public void setBankFlagSrch(String bankFlagSrch) {
		this.bankFlagSrch = bankFlagSrch;
	}

	public String getAccountIdSrch() {
		return accountIdSrch;
	}

	public void setAccountIdSrch(String accountIdSrch) {
		this.accountIdSrch = accountIdSrch;
	}

	public String getSeqNoSrch() {
		return seqNoSrch;
	}

	public void setSeqNoSrch(String seqNoSrch) {
		this.seqNoSrch = seqNoSrch;
	}

	public String getBankSeqNoSrch() {
		return bankSeqNoSrch;
	}

	public void setBankSeqNoSrch(String bankSeqNoSrch) {
		this.bankSeqNoSrch = bankSeqNoSrch;
	}

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	/**
	 * txDateS
	 * @return the txDateS
	 */
	
	public String getTxDateS() {
		return txDateS;
	}

	/**
	 * @param txDateS the txDateS to set
	 */
	
	public void setTxDateS(String txDateS) {
		this.txDateS = txDateS;
	}

	/**
	 * txTimeS
	 * @return the txTimeS
	 */
	
	public String getTxTimeS() {
		return txTimeS;
	}

	/**
	 * @param txTimeS the txTimeS to set
	 */
	
	public void setTxTimeS(String txTimeS) {
		this.txTimeS = txTimeS;
	}

}