package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 企业信息类
 * @author NXL
 *
 */
public class CompanyInfoInstRequesetBean implements Serializable {

	@ApiModelProperty(value = "证件类型字符串")
	private String idType;//证件类型字符串
	@ApiModelProperty(value = "证件类型")
	private String cardType;//证件类型
	@ApiModelProperty(value = "证件号码")
	private String idNo;//证件号码
	@ApiModelProperty(value = "企业名称")
	private String name;//企业名称
	@ApiModelProperty(value = "手机号码")
	private String mobile;//手机号码
	@ApiModelProperty(value = "对公账号/银行账号")
	private String account;//对公账号/银行账号
	@ApiModelProperty(value = "营业执照编号")
	private String busId;//营业执照编号
	@ApiModelProperty(value = "税务登记号")
	private String taxId;//税务登记号
	@ApiModelProperty(value = "修改说明")
	private String remark;//修改说明
	@ApiModelProperty(value = "用户id")
	private String userId;//用户id
	@ApiModelProperty(value = "电子账号")
	private String accountId;//电子账号
	//
	@ApiModelProperty(value = "所属银行")
	private String bankName;//所属银行
	@ApiModelProperty(value = "银联号")
	private String payAllianceCode;//银联号
	@ApiModelProperty(value = "银行id")
	private String bankId;//银行id
	
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
}
