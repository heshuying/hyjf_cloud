package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiasq
 * @version UserVO, v0.1 2018/1/21 22:39
 */
public class UserCustomizeShowVO implements Serializable {

	@ApiModelProperty(value = "用户id")
	private Integer userId;
	@ApiModelProperty(value = "用户名")
	private String username;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "用户是否锁定,0未锁定,1锁定")
	private Integer status;
	@ApiModelProperty(value = "是否开户,0未开户,1已开户")
	private Integer openAccount;

	@ApiModelProperty(value = "账户开通平台 0pc 1微信 2安卓 3IOS 4其他")
	private Integer regEsb;

	@ApiModelProperty(value = "app头像的url")
	private String iconUrl;
	@ApiModelProperty(value = "测评到期时间")
	private Date evaluationExpiredTime;

	@ApiModelProperty(value = "是否是第三方推送用户(0:否,1:是)")
	private Integer isInstFlag;
	@ApiModelProperty(value = "第三方机构编号")
	private String instCode;
	@ApiModelProperty(value = "账户开通平台 0pc 1微信 2安卓 3IOS 4其他")
	private Integer accountEsb;
	@ApiModelProperty(value = "新手标志位：0新手 1老手")
	private Integer investflag;
	@ApiModelProperty(value = "用户类型 0普通用户 1企业用户")
	private Integer userType;
	@ApiModelProperty(value = "缴费授权状态  0：未授权1：已授权")
	private Integer paymentAuthStatus;
	@ApiModelProperty(value = "是否设置了交易密码 0未设置 1已设置")
	private Integer isSetPassword;
	@ApiModelProperty(value = "是否银行开户,0未开户,1已开户")
	private Integer bankOpenAccount;
	@ApiModelProperty(value = "是否已进行风险测评(0:未测评,1:已测评)")
	private Integer isEvaluationFlag;
	@ApiModelProperty(value = "注册ip")
	private String regIp;
	@ApiModelProperty(value = "注册时间")
	private Date regTime;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Date getEvaluationExpiredTime() {
		return evaluationExpiredTime;
	}

	public void setEvaluationExpiredTime(Date evaluationExpiredTime) {
		this.evaluationExpiredTime = evaluationExpiredTime;
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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOpenAccount() {
		return openAccount;
	}

	public void setOpenAccount(Integer openAccount) {
		this.openAccount = openAccount;
	}



	public Integer getRegEsb() {
		return regEsb;
	}

	public void setRegEsb(Integer regEsb) {
		this.regEsb = regEsb;
	}


	public Integer getIsInstFlag() {
		return isInstFlag;
	}

	public void setIsInstFlag(Integer isInstFlag) {
		this.isInstFlag = isInstFlag;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public Integer getAccountEsb() {
		return accountEsb;
	}

	public void setAccountEsb(Integer accountEsb) {
		this.accountEsb = accountEsb;
	}

	public Integer getInvestflag() {
		return investflag;
	}

	public void setInvestflag(Integer investflag) {
		this.investflag = investflag;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getPaymentAuthStatus() {
		return paymentAuthStatus;
	}

	public void setPaymentAuthStatus(Integer paymentAuthStatus) {
		this.paymentAuthStatus = paymentAuthStatus;
	}

	public Integer getIsSetPassword() {
		return isSetPassword;
	}

	public void setIsSetPassword(Integer isSetPassword) {
		this.isSetPassword = isSetPassword;
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


	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
