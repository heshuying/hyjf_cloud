/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Albert
 * @version AdminAccedeListCustomizeVO.java, v0.1 2018年7月21日 上午9:45:46
 */
public class AdminAccedeListCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "加入订单号")
	private String planOrderId;

	@ApiModelProperty(value = "计划编号")
	private String debtPlanNid;

	@ApiModelProperty(value = "用户ID")
	private String userId;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "用户属性")
	private String userAttribute;

	@ApiModelProperty(value = "锁定期")
	private String debtLockPeriod;

	@ApiModelProperty(value = "天/月")
	private Integer isMonth;

	@ApiModelProperty(value = "推荐人ID")
	private String refereeUserId;

	@ApiModelProperty(value = "推荐人")
	private String refereeUserName;
	
	@ApiModelProperty(value = "推荐人用户属性")
	private String recommendAttr;

	@ApiModelProperty(value = "推荐人用户属性")
	private String refereeUseruserAttribute;

	@ApiModelProperty(value = "加入金额")
	private String accedeAccount;

	@ApiModelProperty(value = "已投资金额")
	private String alreadyInvest;

	@ApiModelProperty(value = "平台 ：0PC，1微信，2安卓APP，3IOS，4其他")
	private String platform;

	@ApiModelProperty(value = "订单状态：0自动投标中 1锁定中 2退出中 3已退出")
	private String orderStatus;

	@ApiModelProperty(value = "加入时间")
	private String createTime;

	@ApiModelProperty(value = "计息时间")
	private String countInterestTime;

	@ApiModelProperty(value = "协议发送状态 ：0未发送 1已发送")
	private String sendStatus;

	@ApiModelProperty(value = "提成计算状态:0:未计算,1:已计算'，2:计算失败")
	private String commissionStatus;

	@ApiModelProperty(value = "提成计算时间")
	private String commissionCountTime;

	@ApiModelProperty(value = "可投金额")
	private String availableInvestAccount;

	@ApiModelProperty(value = "计划名称")
	private String debtPlanName;

	@ApiModelProperty(value = "预期年化收益")
	private String expectApr;

	@ApiModelProperty(value = "投资状态")
	private String debtPlanStatus;

	@ApiModelProperty(value = "投资类型")
	private String tenderType;

	@ApiModelProperty(value = "优惠券")
	private String couponCode;
	
	@ApiModelProperty(value = "待(收)还总额")
	private String waitTotal;

	@ApiModelProperty(value = "待(收)还本金")
	private String waitCaptical;

	@ApiModelProperty(value = "待(收)还利息")
	private String waitInterest;

	@ApiModelProperty(value = "返回码")
	private String respCode;
	
	@ApiModelProperty(value = "返回错误信息")
	private String respDesc;
	
	@ApiModelProperty(value = "推荐人部门信息")
	private String inviteUserAttributeName;
	
	@ApiModelProperty(value = "推荐人部门信息")
	private String inviteUserRegionname;
	
	@ApiModelProperty(value = "推荐人部门信息")
	private String inviteUserBranchname;
	
	@ApiModelProperty(value = "推荐人部门信息")
	private String inviteUserDepartmentname;

	@ApiModelProperty(value = "推荐人当前信息")
	private String inviteUserRegionname1;
	
	@ApiModelProperty(value = "推荐人当前信息")
	private String inviteUserBranchname1;
	
	@ApiModelProperty(value = "推荐人当前信息")
	private String inviteUserDepartmentname1;

	@ApiModelProperty(value = "投资人当前信息")
	private String inviteUserRegionname2;
	
	@ApiModelProperty(value = "投资人当前信息")
	private String inviteUserBranchname2;
	
	@ApiModelProperty(value = "投资人当前信息")
	private String inviteUserDepartmentname2;
	
	@ApiModelProperty(value = "加入计划时推荐人(不变)")
	private String inviteName;
	
	@ApiModelProperty(value = "推荐人真实姓名")
	private String refereeTrueName;
	
	private String inviteTrueName;
	
	private String inviteUserId;

	@ApiModelProperty(value = "加入金额合计")
	private String sumAccedeAccount;
	
	@ApiModelProperty(value = "已投资金额")
	private String sumAlreadyInvest;
	
	@ApiModelProperty(value = "待(收)还总额")
	private String sumWaitTotal;
	
	@ApiModelProperty(value = "待(收)还本金")
	private String sumWaitCaptical;
	
	@ApiModelProperty(value = "待(收)还利息")
	private String sumWaitInterest;
	
	@ApiModelProperty(value = "用户当前属性")
	private String attribute;

	@ApiModelProperty(value = "合同状态")
	private String contractStatus;

	@ApiModelProperty(value = "合同编号")
	private String contractNumber;

	@ApiModelProperty(value = "合同名称")
	private String contractName;

	@ApiModelProperty(value = "模板编号")
	private String templetId;

	@ApiModelProperty(value = "合同生成时间")
	private String contractCreateTime;

	@ApiModelProperty(value = "合同签署时间")
	private String contractSignTime;

	@ApiModelProperty(value = "合同下载地址")
	private String downloadUrl;

	@ApiModelProperty(value = "合同查看地址")
	private String viewpdfUrl;

	@ApiModelProperty(value = "脱敏合同地址")
	private String imgUrl;
	
	@ApiModelProperty(value = "退出时间")
	private Integer quitTime;
	
	public String getPlanOrderId() {
		return planOrderId;
	}

	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}

	public String getDebtPlanNid() {
		return debtPlanNid;
	}

	public void setDebtPlanNid(String debtPlanNid) {
		this.debtPlanNid = debtPlanNid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAttribute() {
		return userAttribute;
	}

	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	public String getDebtLockPeriod() {
		return debtLockPeriod;
	}

	public void setDebtLockPeriod(String debtLockPeriod) {
		this.debtLockPeriod = debtLockPeriod;
	}

	public Integer getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(Integer isMonth) {
		this.isMonth = isMonth;
	}

	public String getRefereeUserId() {
		return refereeUserId;
	}

	public void setRefereeUserId(String refereeUserId) {
		this.refereeUserId = refereeUserId;
	}

	public String getRefereeUserName() {
		return refereeUserName;
	}

	public void setRefereeUserName(String refereeUserName) {
		this.refereeUserName = refereeUserName;
	}

	public String getRecommendAttr() {
		return recommendAttr;
	}

	public void setRecommendAttr(String recommendAttr) {
		this.recommendAttr = recommendAttr;
	}

	public String getRefereeUseruserAttribute() {
		return refereeUseruserAttribute;
	}

	public void setRefereeUseruserAttribute(String refereeUseruserAttribute) {
		this.refereeUseruserAttribute = refereeUseruserAttribute;
	}

	public String getAccedeAccount() {
		return accedeAccount;
	}

	public void setAccedeAccount(String accedeAccount) {
		this.accedeAccount = accedeAccount;
	}

	public String getAlreadyInvest() {
		return alreadyInvest;
	}

	public void setAlreadyInvest(String alreadyInvest) {
		this.alreadyInvest = alreadyInvest;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCountInterestTime() {
		return countInterestTime;
	}

	public void setCountInterestTime(String countInterestTime) {
		this.countInterestTime = countInterestTime;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getCommissionStatus() {
		return commissionStatus;
	}

	public void setCommissionStatus(String commissionStatus) {
		this.commissionStatus = commissionStatus;
	}

	public String getCommissionCountTime() {
		return commissionCountTime;
	}

	public void setCommissionCountTime(String commissionCountTime) {
		this.commissionCountTime = commissionCountTime;
	}

	public String getAvailableInvestAccount() {
		return availableInvestAccount;
	}

	public void setAvailableInvestAccount(String availableInvestAccount) {
		this.availableInvestAccount = availableInvestAccount;
	}

	public String getDebtPlanName() {
		return debtPlanName;
	}

	public void setDebtPlanName(String debtPlanName) {
		this.debtPlanName = debtPlanName;
	}

	public String getExpectApr() {
		return expectApr;
	}

	public void setExpectApr(String expectApr) {
		this.expectApr = expectApr;
	}

	public String getDebtPlanStatus() {
		return debtPlanStatus;
	}

	public void setDebtPlanStatus(String debtPlanStatus) {
		this.debtPlanStatus = debtPlanStatus;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getWaitTotal() {
		return waitTotal;
	}

	public void setWaitTotal(String waitTotal) {
		this.waitTotal = waitTotal;
	}

	public String getWaitCaptical() {
		return waitCaptical;
	}

	public void setWaitCaptical(String waitCaptical) {
		this.waitCaptical = waitCaptical;
	}

	public String getWaitInterest() {
		return waitInterest;
	}

	public void setWaitInterest(String waitInterest) {
		this.waitInterest = waitInterest;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getInviteUserAttributeName() {
		return inviteUserAttributeName;
	}

	public void setInviteUserAttributeName(String inviteUserAttributeName) {
		this.inviteUserAttributeName = inviteUserAttributeName;
	}

	public String getInviteUserRegionname() {
		return inviteUserRegionname;
	}

	public void setInviteUserRegionname(String inviteUserRegionname) {
		this.inviteUserRegionname = inviteUserRegionname;
	}

	public String getInviteUserBranchname() {
		return inviteUserBranchname;
	}

	public void setInviteUserBranchname(String inviteUserBranchname) {
		this.inviteUserBranchname = inviteUserBranchname;
	}

	public String getInviteUserDepartmentname() {
		return inviteUserDepartmentname;
	}

	public void setInviteUserDepartmentname(String inviteUserDepartmentname) {
		this.inviteUserDepartmentname = inviteUserDepartmentname;
	}

	public String getInviteUserRegionname1() {
		return inviteUserRegionname1;
	}

	public void setInviteUserRegionname1(String inviteUserRegionname1) {
		this.inviteUserRegionname1 = inviteUserRegionname1;
	}

	public String getInviteUserBranchname1() {
		return inviteUserBranchname1;
	}

	public void setInviteUserBranchname1(String inviteUserBranchname1) {
		this.inviteUserBranchname1 = inviteUserBranchname1;
	}

	public String getInviteUserDepartmentname1() {
		return inviteUserDepartmentname1;
	}

	public void setInviteUserDepartmentname1(String inviteUserDepartmentname1) {
		this.inviteUserDepartmentname1 = inviteUserDepartmentname1;
	}

	public String getInviteUserRegionname2() {
		return inviteUserRegionname2;
	}

	public void setInviteUserRegionname2(String inviteUserRegionname2) {
		this.inviteUserRegionname2 = inviteUserRegionname2;
	}

	public String getInviteUserBranchname2() {
		return inviteUserBranchname2;
	}

	public void setInviteUserBranchname2(String inviteUserBranchname2) {
		this.inviteUserBranchname2 = inviteUserBranchname2;
	}

	public String getInviteUserDepartmentname2() {
		return inviteUserDepartmentname2;
	}

	public void setInviteUserDepartmentname2(String inviteUserDepartmentname2) {
		this.inviteUserDepartmentname2 = inviteUserDepartmentname2;
	}

	public String getInviteName() {
		return inviteName;
	}

	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}

	public String getRefereeTrueName() {
		return refereeTrueName;
	}

	public void setRefereeTrueName(String refereeTrueName) {
		this.refereeTrueName = refereeTrueName;
	}

	public String getInviteTrueName() {
		return inviteTrueName;
	}

	public void setInviteTrueName(String inviteTrueName) {
		this.inviteTrueName = inviteTrueName;
	}

	public String getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getSumAccedeAccount() {
		return sumAccedeAccount;
	}

	public void setSumAccedeAccount(String sumAccedeAccount) {
		this.sumAccedeAccount = sumAccedeAccount;
	}

	public String getSumAlreadyInvest() {
		return sumAlreadyInvest;
	}

	public void setSumAlreadyInvest(String sumAlreadyInvest) {
		this.sumAlreadyInvest = sumAlreadyInvest;
	}

	public String getSumWaitTotal() {
		return sumWaitTotal;
	}

	public void setSumWaitTotal(String sumWaitTotal) {
		this.sumWaitTotal = sumWaitTotal;
	}

	public String getSumWaitCaptical() {
		return sumWaitCaptical;
	}

	public void setSumWaitCaptical(String sumWaitCaptical) {
		this.sumWaitCaptical = sumWaitCaptical;
	}

	public String getSumWaitInterest() {
		return sumWaitInterest;
	}

	public void setSumWaitInterest(String sumWaitInterest) {
		this.sumWaitInterest = sumWaitInterest;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}

	public String getContractCreateTime() {
		return contractCreateTime;
	}

	public void setContractCreateTime(String contractCreateTime) {
		this.contractCreateTime = contractCreateTime;
	}

	public String getContractSignTime() {
		return contractSignTime;
	}

	public void setContractSignTime(String contractSignTime) {
		this.contractSignTime = contractSignTime;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getViewpdfUrl() {
		return viewpdfUrl;
	}

	public void setViewpdfUrl(String viewpdfUrl) {
		this.viewpdfUrl = viewpdfUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(Integer quitTime) {
		this.quitTime = quitTime;
	}

}