package com.hyjf.callcenter.beans;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 分公司
	 */
	private String regionName;
	/**
	 * 分部
	 */
	private String branchName;
	/**
	 * 团队
	 */
	private String departmentName;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 用户角色
	 */
	private String role;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 用户属性
	 */
	private String userProperty;
	/**
	 * 推荐人
	 */
	private String recommendName;
	/**
	 * 注册平台
	 */
	private String registPlat;
	/**
	 * 注册渠道
	 */
	private String registPidName;
	/**
	 * 注册时间
	 */
	private String registTime;
	/**
	 * 汇付开户状态
	 */
	private String openAccount;
	/**
	 * 汇付用户号
	 */
	private String account;
	/**
	 * 汇付客户号
	 */
	private String customerAccount;
	/**
	 * 开户平台
	 */
	private String openAccountPlat;
	/**
	 * 汇付开户时间
	 */
	private String openAccountTime;
	/**
	 * 江西银行开户状态
	 */
	private String bankOpenAccount;
	/**
	 * 江西银行交易密码设置状态
	 */
	private Integer isSetPassword;
	/**
	 * 江西银行电子账号
	 */
	private String bankAccount;
	/**
	 * 江西银行开户时间
	 */
	private String bankOpenTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserProperty() {
		return userProperty;
	}
	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}
	public String getRecommendName() {
		return recommendName;
	}
	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}
	public String getRegistPlat() {
		return registPlat;
	}
	public void setRegistPlat(String registPlat) {
		this.registPlat = registPlat;
	}
	public String getRegistPidName() {
		return registPidName;
	}
	public void setRegistPidName(String registPidName) {
		this.registPidName = registPidName;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getOpenAccount() {
		return openAccount;
	}
	public void setOpenAccount(String openAccount) {
		this.openAccount = openAccount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}
	public String getOpenAccountPlat() {
		return openAccountPlat;
	}
	public void setOpenAccountPlat(String openAccountPlat) {
		this.openAccountPlat = openAccountPlat;
	}
	public String getOpenAccountTime() {
		return openAccountTime;
	}
	public void setOpenAccountTime(String openAccountTime) {
		this.openAccountTime = openAccountTime;
	}
	public String getBankOpenAccount() {
		return bankOpenAccount;
	}
	public void setBankOpenAccount(String bankOpenAccount) {
		this.bankOpenAccount = bankOpenAccount;
	}
	public Integer getIsSetPassword() {
		return isSetPassword;
	}
	public void setIsSetPassword(Integer isSetPassword) {
		this.isSetPassword = isSetPassword;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankOpenTime() {
		return bankOpenTime;
	}
	public void setBankOpenTime(String bankOpenTime) {
		this.bankOpenTime = bankOpenTime;
	}
	

}
