package com.hyjf.am.vo.admin;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hyjf.am.vo.BaseVO;

public class AssetListCustomizeVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 资产编号
	 */
	private String assetId;
	/**
	 * 机构编号
	 */
	private String instCode;
	/**
	 * 产品类型编号
	 */
	private String assetType;
	/**
	 * 机构编号名称
	 */
	private String instName;
	/**
	 * 机构产品类型名称
	 */
	private String assetTypeName;
	/**
	 * 项目编号
	 */
	private String borrowNid;
	/**
	 * 计划编号
	 */
	private String planNid;
	/**
	 * 借款用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 江西银行电子账号
	 */
	private String accountId;
	/**
	 * 真实姓名
	 */
	private String truename;
	/**
	 * 身份证号
	 */
	private String idcard;
	/**
	 * 借款金额
	 */
	private String account;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 还款方式
	 */
	private String borrowStyleName;
	/**
	 * 审核状态
	 */
	private String verifyStatus;
	/**
	 * 项目状态
	 */
	private String status;
	/**
	 * 推送时间
	 */
	private String recieveTime;
	/**
	 * 标签名字
	 */
	private String labelName;
	/**
	 * 开户状态
	 */
	private String bankOpenAccount;
	/*-------add by LSY START---------------------*/
	/**
	 * 金额合计
	 */
	private BigDecimal sumAccount;
	/*-------add by LSY END---------------------*/
	/**
	 * 借款类型
	 */
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * assetId
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * instName
	 * @return the instName
	 */
	public String getInstName() {
		return instName;
	}
	/**
	 * @param instName the instName to set
	 */
	public void setInstName(String instName) {
		this.instName = instName;
	}
	/**
	 * assetTypeName
	 * @return the assetTypeName
	 */
	public String getAssetTypeName() {
		return assetTypeName;
	}
	/**
	 * @param assetTypeName the assetTypeName to set
	 */
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
	/**
	 * borrowNid
	 * @return the borrowNid
	 */
	public String getBorrowNid() {
		return borrowNid;
	}
	/**
	 * @param borrowNid the borrowNid to set
	 */
	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	/**
	 * planNid
	 * @return the planNid
	 */
	public String getPlanNid() {
		return planNid;
	}
	/**
	 * @param planNid the planNid to set
	 */
	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}
	/**
	 * userName
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * mobile
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * accountId
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * truename
	 * @return the truename
	 */
	public String getTruename() {
		return truename;
	}
	/**
	 * @param truename the truename to set
	 */
	public void setTruename(String truename) {
		this.truename = truename;
	}
	/**
	 * idcard
	 * @return the idcard
	 */
	public String getIdcard() {
		return idcard;
	}
	/**
	 * @param idcard the idcard to set
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	/**
	 * account
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * borrowPeriod
	 * @return the borrowPeriod
	 */
	public String getBorrowPeriod() {
		return borrowPeriod;
	}
	/**
	 * @param borrowPeriod the borrowPeriod to set
	 */
	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}
	/**
	 * borrowStyleName
	 * @return the borrowStyleName
	 */
	public String getBorrowStyleName() {
		return borrowStyleName;
	}
	/**
	 * @param borrowStyleName the borrowStyleName to set
	 */
	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}
	/**
	 * verifyStatus
	 * @return the verifyStatus
	 */
	public String getVerifyStatus() {
		return verifyStatus;
	}
	/**
	 * @param verifyStatus the verifyStatus to set
	 */
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	/**
	 * status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * recieveTime
	 * @return the recieveTime
	 */
	public String getRecieveTime() {
		return recieveTime;
	}
	/**
	 * @param recieveTime the recieveTime to set
	 */
	public void setRecieveTime(String recieveTime) {
		this.recieveTime = recieveTime;
	}
	/**
	 * bankOpenAccount
	 * @return the bankOpenAccount
	 */
	public String getBankOpenAccount() {
		return bankOpenAccount;
	}
	/**
	 * @param bankOpenAccount the bankOpenAccount to set
	 */
	public void setBankOpenAccount(String bankOpenAccount) {
		this.bankOpenAccount = bankOpenAccount;
	}
	/**
	 * instCode
	 * @return the instCode
	 */
	
	public String getInstCode() {
		return instCode;
	}
	/**
	 * @param instCode the instCode to set
	 */
	
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	/**
	 * assetType
	 * @return the assetType
	 */
	
	public String getAssetType() {
		return assetType;
	}
	/**
	 * @param assetType the assetType to set
	 */
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	/**
	 * sumAccount
	 * @return the sumAccount
	 */
		
	public BigDecimal getSumAccount() {
		return sumAccount;
			
	}
	/**
	 * @param sumAccount the sumAccount to set
	 */
		
	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
			
	}
	

}
