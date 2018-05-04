package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version UserVO, v0.1 2018/1/21 22:39
 */
public class UserVO extends BaseVO implements Serializable {

	/**
	 * token 登录生成令牌
	 */
	private String token;

	private Integer userId;
	private String username;
	private String mobile;
	private String email;
	/**
	 * 汇付开户账号
	 */
	private Integer openAccount;
	
	/**
	 * 银行开户账号
	 */
	private Integer bankOpenAccount;
	private Integer isCaFlag;

	private Integer sex;
	private Integer borrowerType;
	/**
	 * 图像url
	 */
	private String iconurl;


	/**
	 * 推荐人
	 */
	private String referrerUserName;


	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 真实姓名
	 */
	private String truename;
	/**
	 * 身份证
	 */
	private String idcard;
	/**
	 * 用户角色
	 */
	private String roleId;
	/**
	 * 充值成功短信 0发送1不发送
	 */
	private Integer rechargeSms;

	/**
	 * 提现成功短线 0发送1不发送
	 */
	private Integer withdrawSms;

	/**
	 * 投资成功短信 0发送1不发送
	 */
	private Integer investSms;

	/**
	 * 是否发送投资协议邮件 0发送 1不发送
	 */
	private Integer isSmtp;
	/**
	 * 回收成功短信 0发送1不发送
	 */
	private Integer recieveSms;
	/**
	 * 是否设置过交易密码 0未设置 1已设置
	 */
	private Integer isSetPassword;
	/**
	 * 用户类型  0普通用户 1企业账户
	 */
	private Integer userType;

	/**
	 * 是否测评:0:未测评,1:已测评
	 */
	private Integer isEvaluationFlag;


	/**
	 * 登录验证
	 */
	private String salt;
	private String password;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getOpenAccount() {
		return openAccount;
	}

	public void setOpenAccount(Integer openAccount) {
		this.openAccount = openAccount;
	}

	public Integer getIsSetPassword() {
		return isSetPassword;
	}

	public void setIsSetPassword(Integer isSetPassword) {
		this.isSetPassword = isSetPassword;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getBankOpenAccount() {
		return bankOpenAccount;
	}

	public void setBankOpenAccount(Integer bankOpenAccount) {
		this.bankOpenAccount = bankOpenAccount;
	}

	public Integer getIsEvaluationFlag() {
		return isEvaluationFlag;
	}

	public void setIsEvaluationFlag(Integer isEvaluationFlag) {
		this.isEvaluationFlag = isEvaluationFlag;
	}

	public Integer getIsCaFlag() {
		return isCaFlag;
	}

	public void setIsCaFlag(Integer isCaFlag) {
		this.isCaFlag = isCaFlag;
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getReferrerUserName() {
		return referrerUserName;
	}

	public void setReferrerUserName(String referrerUserName) {
		this.referrerUserName = referrerUserName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getRechargeSms() {
		return rechargeSms;
	}

	public void setRechargeSms(Integer rechargeSms) {
		this.rechargeSms = rechargeSms;
	}

	public Integer getWithdrawSms() {
		return withdrawSms;
	}

	public void setWithdrawSms(Integer withdrawSms) {
		this.withdrawSms = withdrawSms;
	}

	public Integer getInvestSms() {
		return investSms;
	}

	public void setInvestSms(Integer investSms) {
		this.investSms = investSms;
	}

	public Integer getIsSmtp() {
		return isSmtp;
	}

	public void setIsSmtp(Integer isSmtp) {
		this.isSmtp = isSmtp;
	}

	public Integer getRecieveSms() {
		return recieveSms;
	}

	public void setRecieveSms(Integer recieveSms) {
		this.recieveSms = recieveSms;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(Integer borrowerType) {
		this.borrowerType = borrowerType;
	}
}
