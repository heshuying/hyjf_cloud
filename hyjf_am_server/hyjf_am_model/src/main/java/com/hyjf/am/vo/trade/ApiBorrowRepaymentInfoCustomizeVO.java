/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.trade;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
* @author Zha Daojian
* @date 2018/8/28 9:57
* @param 
* @return 
**/
public class ApiBorrowRepaymentInfoCustomizeVO implements Serializable {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	// ========================参数=============================
	@ApiModelProperty(value = "第三方接口机构编号")
	private String instCode;

	@ApiModelProperty(value = "资产编号")
	private String assetId;

	@ApiModelProperty(value = "投资订单号")
	private String accedeOrderId;

	@ApiModelProperty(value = "借款编号")
	private String borrowNid;

	@ApiModelProperty(value = "计划编号")
	private String planNid;

	@ApiModelProperty(value = "借款人ID")
	private String userId;

	@ApiModelProperty(value = "借款人用户名")
	private String borrowUserName;

	@ApiModelProperty(value = "类型")
	private String borrowStyle;

	@ApiModelProperty(value = "借款标题")
	private String borrowName;

	@ApiModelProperty(value = "项目类型id")
	private String projectType;

	@ApiModelProperty(value = "项目类型名称")
	private String projectTypeName;

	@ApiModelProperty(value = "借款期限")
	private String borrowPeriod;

	@ApiModelProperty(value = "年化收益")
	private String borrowApr;

	@ApiModelProperty(value = "借款金额")
	private String borrowAccount;

	@ApiModelProperty(value = "借到金额")
	private String borrowAccountYes;

	@ApiModelProperty(value = "还款方式")
	private String repayType;

	@ApiModelProperty(value = "投资人姓名")
	private String recoverTrueName;

	@ApiModelProperty(value = "投资人用户名")
	private String recoverUserName;

	@ApiModelProperty(value = "投资人用户属性（当前）")
	private String recoverUserAttribute;

	@ApiModelProperty(value = "投资人所属一级分部（当前）")
	private String recoverRegionName;

	@ApiModelProperty(value = "投资人所属二级分部（当前）")
	private String recoverBranchName;

	@ApiModelProperty(value = "投资人所属团队（当前)")
	private String recoverDepartmentName;

	@ApiModelProperty(value = "推荐人（当前")
	private String referrerName;

	@ApiModelProperty(value = "推荐人ID（当前）")
	private String referrerUserId;

	@ApiModelProperty(value = "推荐人姓名（当前)")
	private String referrerTrueName;

	@ApiModelProperty(value = "推荐人所属一级分部（当前）")
	private String referrerRegionName;

	@ApiModelProperty(value = "推荐人所属二级分部（当前）")
	private String referrerBranchName;

	@ApiModelProperty(value = "推荐人所属团队（当前）")
	private String referrerDepartmentName;

	@ApiModelProperty(value = "投资期限")
	private String recoverPeriod;

	@ApiModelProperty(value = "投资金额")
	private String recoverTotal;

	@ApiModelProperty(value = "应还本金")
	private String recoverCapital;

	@ApiModelProperty(value = "应还利息")
	private String recoverInterest;

	@ApiModelProperty(value = "应还本息")
	private String recoverAccount;

	@ApiModelProperty(value = "管理费")
	private String recoverFee;

	@ApiModelProperty(value = "已还本金")
	private String recoverCapitalYes;

	@ApiModelProperty(value = "已还利息")
	private String recoverInterestYes;

	@ApiModelProperty(value = "已换本息")
	private String recoverAccountYes;

	@ApiModelProperty(value = "未还本金")
	private String recoverCapitalWait;

	@ApiModelProperty(value = "未还利息")
	private String recoverInterestWait;

	@ApiModelProperty(value = "未还本息")
	private String recoverAccountWait;

	@ApiModelProperty(value = "还款状态")
	private String status;

	@ApiModelProperty(value = "最后还款日")
	private String recoverLastTime;

	@ApiModelProperty(value = "实际还款时间")
	private String repayActionTime;

	@ApiModelProperty(value = "还款订单号")
	private String repayOrdid;

	@ApiModelProperty(value = "还款批次号")
	private String repayBatchNo;

	@ApiModelProperty(value = "机构名称")
	private String instName;

	@ApiModelProperty(value = "管理费费率")
	private String manageFeeRate;

	@ApiModelProperty(value = "服务费率")
	private String serviceRate;

	@ApiModelProperty(value = "服务费")
	private String serviceFee;

	@ApiModelProperty(value = "应还总额")
	private String recoverSumTotal;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAccedeOrderId() {
		return accedeOrderId;
	}

	public void setAccedeOrderId(String accedeOrderId) {
		this.accedeOrderId = accedeOrderId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getBorrowAccountYes() {
		return borrowAccountYes;
	}

	public void setBorrowAccountYes(String borrowAccountYes) {
		this.borrowAccountYes = borrowAccountYes;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getRecoverTrueName() {
		return recoverTrueName;
	}

	public void setRecoverTrueName(String recoverTrueName) {
		this.recoverTrueName = recoverTrueName;
	}

	public String getRecoverUserName() {
		return recoverUserName;
	}

	public void setRecoverUserName(String recoverUserName) {
		this.recoverUserName = recoverUserName;
	}

	public String getRecoverUserAttribute() {
		return recoverUserAttribute;
	}

	public void setRecoverUserAttribute(String recoverUserAttribute) {
		this.recoverUserAttribute = recoverUserAttribute;
	}

	public String getRecoverRegionName() {
		return recoverRegionName;
	}

	public void setRecoverRegionName(String recoverRegionName) {
		this.recoverRegionName = recoverRegionName;
	}

	public String getRecoverBranchName() {
		return recoverBranchName;
	}

	public void setRecoverBranchName(String recoverBranchName) {
		this.recoverBranchName = recoverBranchName;
	}

	public String getRecoverDepartmentName() {
		return recoverDepartmentName;
	}

	public void setRecoverDepartmentName(String recoverDepartmentName) {
		this.recoverDepartmentName = recoverDepartmentName;
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

	public String getRecoverPeriod() {
		return recoverPeriod;
	}

	public void setRecoverPeriod(String recoverPeriod) {
		this.recoverPeriod = recoverPeriod;
	}

	public String getRecoverTotal() {
		return recoverTotal;
	}

	public void setRecoverTotal(String recoverTotal) {
		this.recoverTotal = recoverTotal;
	}

	public String getRecoverCapital() {
		return recoverCapital;
	}

	public void setRecoverCapital(String recoverCapital) {
		this.recoverCapital = recoverCapital;
	}

	public String getRecoverInterest() {
		return recoverInterest;
	}

	public void setRecoverInterest(String recoverInterest) {
		this.recoverInterest = recoverInterest;
	}

	public String getRecoverAccount() {
		return recoverAccount;
	}

	public void setRecoverAccount(String recoverAccount) {
		this.recoverAccount = recoverAccount;
	}

	public String getRecoverFee() {
		return recoverFee;
	}

	public void setRecoverFee(String recoverFee) {
		this.recoverFee = recoverFee;
	}

	public String getRecoverCapitalYes() {
		return recoverCapitalYes;
	}

	public void setRecoverCapitalYes(String recoverCapitalYes) {
		this.recoverCapitalYes = recoverCapitalYes;
	}

	public String getRecoverInterestYes() {
		return recoverInterestYes;
	}

	public void setRecoverInterestYes(String recoverInterestYes) {
		this.recoverInterestYes = recoverInterestYes;
	}

	public String getRecoverAccountYes() {
		return recoverAccountYes;
	}

	public void setRecoverAccountYes(String recoverAccountYes) {
		this.recoverAccountYes = recoverAccountYes;
	}

	public String getRecoverCapitalWait() {
		return recoverCapitalWait;
	}

	public void setRecoverCapitalWait(String recoverCapitalWait) {
		this.recoverCapitalWait = recoverCapitalWait;
	}

	public String getRecoverInterestWait() {
		return recoverInterestWait;
	}

	public void setRecoverInterestWait(String recoverInterestWait) {
		this.recoverInterestWait = recoverInterestWait;
	}

	public String getRecoverAccountWait() {
		return recoverAccountWait;
	}

	public void setRecoverAccountWait(String recoverAccountWait) {
		this.recoverAccountWait = recoverAccountWait;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	public String getRepayActionTime() {
		return repayActionTime;
	}

	public void setRepayActionTime(String repayActionTime) {
		this.repayActionTime = repayActionTime;
	}

	public String getRepayOrdid() {
		return repayOrdid;
	}

	public void setRepayOrdid(String repayOrdid) {
		this.repayOrdid = repayOrdid;
	}

	public String getRepayBatchNo() {
		return repayBatchNo;
	}

	public void setRepayBatchNo(String repayBatchNo) {
		this.repayBatchNo = repayBatchNo;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getManageFeeRate() {
		return manageFeeRate;
	}

	public void setManageFeeRate(String manageFeeRate) {
		this.manageFeeRate = manageFeeRate;
	}

	public String getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(String serviceRate) {
		this.serviceRate = serviceRate;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getRecoverSumTotal() {
		return recoverSumTotal;
	}

	public void setRecoverSumTotal(String recoverSumTotal) {
		this.recoverSumTotal = recoverSumTotal;
	}
}
