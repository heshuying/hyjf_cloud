package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 汇计划加入明细
 * 
 * @author nxl
 * @date 2018年8月12日 上午10:19:06
 */
public class NifaFieldDefinitionCustomizeVO {
	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "借款用途限制")
	private String borrowingRestrictions;
	@ApiModelProperty(value = "借款放款日判断依据")
	private String judgmentsBased;
	@ApiModelProperty(value = "还款日规则说明")
	private String repayDateRule;
	@ApiModelProperty(value = "逾期定义")
	private String overdueDefinition;
	@ApiModelProperty(value = "逾期还款责任")
	private String overdueResponsibility;
	@ApiModelProperty(value = "逾期还款流程")
	private String overdueProcess;
	@ApiModelProperty(value = "创建用户id")
	private Integer createUserId;
	@ApiModelProperty(value = "更新用户id")
	private Integer updateUserId;
	@ApiModelProperty(value = "创建时间(格式化)")
	private String createDate;
	@ApiModelProperty(value = "修改时间(格式化)")
	private String updateDate;
	@ApiModelProperty(value = "创建用户名")
	private String  createUserName;
	@ApiModelProperty(value = "更新用户名")
	private String updateUserName;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBorrowingRestrictions() {
		return borrowingRestrictions;
	}

	public void setBorrowingRestrictions(String borrowingRestrictions) {
		this.borrowingRestrictions = borrowingRestrictions;
	}

	public String getJudgmentsBased() {
		return judgmentsBased;
	}

	public void setJudgmentsBased(String judgmentsBased) {
		this.judgmentsBased = judgmentsBased;
	}

	public String getRepayDateRule() {
		return repayDateRule;
	}

	public void setRepayDateRule(String repayDateRule) {
		this.repayDateRule = repayDateRule;
	}

	public String getOverdueDefinition() {
		return overdueDefinition;
	}

	public void setOverdueDefinition(String overdueDefinition) {
		this.overdueDefinition = overdueDefinition;
	}

	public String getOverdueResponsibility() {
		return overdueResponsibility;
	}

	public void setOverdueResponsibility(String overdueResponsibility) {
		this.overdueResponsibility = overdueResponsibility;
	}

	public String getOverdueProcess() {
		return overdueProcess;
	}

	public void setOverdueProcess(String overdueProcess) {
		this.overdueProcess = overdueProcess;
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
