package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 合同模版约定条款表
 * 
 * @author nxl
 * @date 2018年8月12日 上午10:19:06
 */
public class NifaContractTemplateCustomizeVO {
	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "模版编号")
	private String templetNid;

	@ApiModelProperty(value = "正常还款定义")
	private String normalDefinition;

	@ApiModelProperty(value = "提前还款定义")
	private String prepaymentDefinition;

	@ApiModelProperty(value = "借款人承诺与保证")
	private String borrowerPromises;

	@ApiModelProperty(value = "出借人承诺与保证")
	private String lenderPromises;

	@ApiModelProperty(value = "借款人还款义务")
	private String borrowerObligation;

	@ApiModelProperty(value = "保密")
	private String confidentiality;

	@ApiModelProperty(value = "违约")
	private String breachContract;

	@ApiModelProperty(value = "法律适用")
	private String applicableLaw;

	@ApiModelProperty(value = "争议解决")
	private String disputeResolution;

	@ApiModelProperty(value = "其他条款")
	private String otherConditions;

	@ApiModelProperty(value = "创建人")
	private Integer createUserId;

	@ApiModelProperty(value = "修改人")
	private Integer updateUserId;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "最后修改时间")
	private Date updateTime;

	@ApiModelProperty(value = "创建时间(格式化)")
	private String createDate;
	@ApiModelProperty(value = "修改时间(格式化)")
	private String updateDate;
	@ApiModelProperty(value = "创建用户名")
	private String  createUserName;
	@ApiModelProperty(value = "更新用户名")
	private String updateUserName;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTempletNid() {
		return templetNid;
	}

	public void setTempletNid(String templetNid) {
		this.templetNid = templetNid == null ? null : templetNid.trim();
	}

	public String getNormalDefinition() {
		return normalDefinition;
	}

	public void setNormalDefinition(String normalDefinition) {
		this.normalDefinition = normalDefinition == null ? null : normalDefinition.trim();
	}

	public String getPrepaymentDefinition() {
		return prepaymentDefinition;
	}

	public void setPrepaymentDefinition(String prepaymentDefinition) {
		this.prepaymentDefinition = prepaymentDefinition == null ? null : prepaymentDefinition.trim();
	}

	public String getBorrowerPromises() {
		return borrowerPromises;
	}

	public void setBorrowerPromises(String borrowerPromises) {
		this.borrowerPromises = borrowerPromises == null ? null : borrowerPromises.trim();
	}

	public String getLenderPromises() {
		return lenderPromises;
	}

	public void setLenderPromises(String lenderPromises) {
		this.lenderPromises = lenderPromises == null ? null : lenderPromises.trim();
	}

	public String getBorrowerObligation() {
		return borrowerObligation;
	}

	public void setBorrowerObligation(String borrowerObligation) {
		this.borrowerObligation = borrowerObligation == null ? null : borrowerObligation.trim();
	}

	public String getConfidentiality() {
		return confidentiality;
	}

	public void setConfidentiality(String confidentiality) {
		this.confidentiality = confidentiality == null ? null : confidentiality.trim();
	}

	public String getBreachContract() {
		return breachContract;
	}

	public void setBreachContract(String breachContract) {
		this.breachContract = breachContract == null ? null : breachContract.trim();
	}

	public String getApplicableLaw() {
		return applicableLaw;
	}

	public void setApplicableLaw(String applicableLaw) {
		this.applicableLaw = applicableLaw == null ? null : applicableLaw.trim();
	}

	public String getDisputeResolution() {
		return disputeResolution;
	}

	public void setDisputeResolution(String disputeResolution) {
		this.disputeResolution = disputeResolution == null ? null : disputeResolution.trim();
	}

	public String getOtherConditions() {
		return otherConditions;
	}

	public void setOtherConditions(String otherConditions) {
		this.otherConditions = otherConditions == null ? null : otherConditions.trim();
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}
