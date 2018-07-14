package com.hyjf.am.vo.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 汇计划加入明细
 * 
 * @author nxl
 * @date 2018年8月12日 上午10:19:06
 */
public class AdminPlanAccedeListCustomizeVO {

	/**
	 * 加入订单号
	 */
	private String planOrderId;
	/**
	 * 计划编号
	 */
	private String debtPlanNid;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户属性
	 */
	private String userAttribute;
	/**
	 * 锁定期
	 */
	private String debtLockPeriod;
	/**
	 * 锁定期 0 天 1 个月
	 */
	private Integer isMonth;
	/**
	 * 推荐人ID
	 */
	private String refereeUserId;
	/**
	 * 推荐人
	 */
	private String refereeUserName;
	
	/**
	 * 推荐人用户属性
	 */
	private String recommendAttr;
	/**
	 * 推荐人用户属性
	 */
	private String refereeUseruserAttribute;
	/**
	 * 加入金额
	 */
	private String accedeAccount;
	/**
	 * 已投资金额
	 */
	private String alreadyInvest;
	/**
	 * 平台 ：0PC，1微信，2安卓APP，3IOS，4其他'
	 */
	private String platform;
	/**
	 * 订单状态：0自动投标中 1锁定中 2退出中 3已退出
	 */
	private String orderStatus;
	/**
	 * 加入时间
	 */
	private String createTime;
	/**
	 * 计息时间
	 */
	private String countInterestTime;
	/**
	 * 协议发送状态 ：0未发送 1已发送
	 */
	private String sendStatus;
	/**
	 * 提成计算状态:0:未计算,1:已计算'，2:计算失败,
	 */
	private String commissionStatus;
	/**
	 * 提成计算时间
	 */
	private String commissionCountTime;
	/**
	 * 可投金额
	 */
	private String availableInvestAccount;
	/**
	 * 计划名称
	 */
	private String debtPlanName;
	/**
	 * 预期年化收益
	 */
	private String expectApr;
	/**
	 * 投资状态
	 */
	private String debtPlanStatus;
	/**
	 * 投资类型
	 */
	private String tenderType;
	/**
	 * 优惠券
	 */
	private String couponCode;
	
	/** * new add */
	/**
	 * 待(收)还总额
	 */
	private String waitTotal;
	/**
	 * 待(收)还本金
	 */
	private String waitCaptical;
	/**
	 * 待(收)还利息
	 */
	private String waitInterest;

	/**
	 * 返回码
	 */
	private String respCode;
	
	/**
	 * 返回错误信息
	 */
	private String respDesc;
	
	/**
	 * 推荐人部门信息
	 */
	private String inviteUserAttributeName;
	private String inviteUserRegionname;
	private String inviteUserBranchname;
	private String inviteUserDepartmentname;
	/**
	 * 推荐人当前信息
	 */
	private String inviteUserRegionname1;
	private String inviteUserBranchname1;
	private String inviteUserDepartmentname1;
	/**
	 * 投资人当前信息
	 */
	private String inviteUserRegionname2;
	private String inviteUserBranchname2;
	private String inviteUserDepartmentname2;
	

	/**
	 * 加入计划时推荐人(不变)
	 */
	private String inviteName;
	
	//推荐人 真是姓名
	
	private String refereeTrueName;
	
	private String inviteTrueName;
	private String inviteUserId;
	
	/*-----add by LSY START---------*/
	/**
	 * 加入金额合计
	 */
	private String sumAccedeAccount;
	
	/**
	 * 已投资金额
	 */
	private String sumAlreadyInvest;
	
	/**
	 * 待(收)还总额
	 */
	private String sumWaitTotal;
	
	/**
	 * 待(收)还本金
	 */
	private String sumWaitCaptical;
	
	/**
	 * 待(收)还利息
	 */
	private String sumWaitInterest;
	
	/**
	 * 剩余可投金额合计
	 */
	private String sumAvailableInvestAccount;
	
	/**
	 * 冻结金额合计
	 */
	private String sumFrostAccount;
	
	/**
	 * 公允价值合计
	 */
	private String sumFairValue;
	/*-----add by LSY END---------*/
	
	/**
	 * 用户当前属性
	 */
	private String attribute;


	/**
	 * 合同状态
	 */
	private String contractStatus;

	/**
	 * 合同编号
	 */
	private String contractNumber;

	/**
	 * 合同名称
	 */
	private String contractName;

	/**
	 * 模板编号
	 */
	private String templetId;

	/**
	 * 合同生成时间
	 */
	private String contractCreateTime;

	/**
	 * 合同签署时间
	 */
	private String contractSignTime;

	/**
	 * 合同下载地址
	 */
	private String downloadUrl;

	/**
	 * 合同查看地址
	 */
	private String viewpdfUrl;

	/**
	 * 脱敏合同地址
	 */
	private String imgUrl;
	
	/**
	 * 自动投标进度
	 */
	private String autoBidProgress;
	
	/**
	 * 冻结金额
	 */
	private String frostAccount;
	
	/**
	 * 公允价值
	 */
	private String fairValue;
	
	/**
	 * 实际年化收益率
	 */
	private String actualApr;
	
	/**
	 * 投资笔数
	 */
	private String investCounts;
	
	/**
	 * 匹配期
	 */
	private String matchDates;
	
	/**
	 * 计算用加入金额
	 */
	private BigDecimal jAccedeAccount;
	
	/**
	 * 计算用已投资金额
	 */
	private BigDecimal jalreadyInvest;
	
	
	
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
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
	public String getWaitTotal() {
		return waitTotal;
	}
	public void setWaitTotal(String waitTotal) {
		this.waitTotal = waitTotal;
	}
	public String getInviteName() {
		return inviteName;
	}
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
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
	public String getRecommendAttr() {
		return recommendAttr;
	}
	public void setRecommendAttr(String recommendAttr) {
		this.recommendAttr = recommendAttr;
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
	public String getInviteUserAttributeName() {
		return inviteUserAttributeName;
	}
	public void setInviteUserAttributeName(String inviteUserAttributeName) {
		this.inviteUserAttributeName = inviteUserAttributeName;
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
	/**
	 * sumAccedeAccount
	 * @return the sumAccedeAccount
	 */
		
	public String getSumAccedeAccount() {
		return sumAccedeAccount;
			
	}
	/**
	 * @param sumAccedeAccount the sumAccedeAccount to set
	 */
		
	public void setSumAccedeAccount(String sumAccedeAccount) {
		this.sumAccedeAccount = sumAccedeAccount;
			
	}
	/**
	 * sumAlreadyInvest
	 * @return the sumAlreadyInvest
	 */
		
	public String getSumAlreadyInvest() {
		return sumAlreadyInvest;
			
	}
	/**
	 * @param sumAlreadyInvest the sumAlreadyInvest to set
	 */
		
	public void setSumAlreadyInvest(String sumAlreadyInvest) {
		this.sumAlreadyInvest = sumAlreadyInvest;
			
	}
	/**
	 * sumWaitTotal
	 * @return the sumWaitTotal
	 */
		
	public String getSumWaitTotal() {
		return sumWaitTotal;
			
	}
	/**
	 * @param sumWaitTotal the sumWaitTotal to set
	 */
		
	public void setSumWaitTotal(String sumWaitTotal) {
		this.sumWaitTotal = sumWaitTotal;
			
	}
	/**
	 * sumWaitCaptical
	 * @return the sumWaitCaptical
	 */
		
	public String getSumWaitCaptical() {
		return sumWaitCaptical;
			
	}
	/**
	 * @param sumWaitCaptical the sumWaitCaptical to set
	 */
		
	public void setSumWaitCaptical(String sumWaitCaptical) {
		this.sumWaitCaptical = sumWaitCaptical;
			
	}
	/**
	 * sumWaitInterest
	 * @return the sumWaitInterest
	 */
		
	public String getSumWaitInterest() {
		return sumWaitInterest;
			
	}
	/**
	 * @param sumWaitInterest the sumWaitInterest to set
	 */
		
	public void setSumWaitInterest(String sumWaitInterest) {
		this.sumWaitInterest = sumWaitInterest;
			
	}
	public Integer getIsMonth() {
		return isMonth;
	}
	public void setIsMonth(Integer isMonth) {
		this.isMonth = isMonth;
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
	public String getAutoBidProgress() {
		return autoBidProgress;
	}
	public void setAutoBidProgress(String autoBidProgress) {
		this.autoBidProgress = autoBidProgress;
	}
	public String getFrostAccount() {
		return frostAccount;
	}
	public void setFrostAccount(String frostAccount) {
		this.frostAccount = frostAccount;
	}
	public String getFairValue() {
		return fairValue;
	}
	public void setFairValue(String fairValue) {
		this.fairValue = fairValue;
	}
	public String getActualApr() {
		return actualApr;
	}
	public void setActualApr(String actualApr) {
		this.actualApr = actualApr;
	}
	public String getInvestCounts() {
		return investCounts;
	}
	public void setInvestCounts(String investCounts) {
		this.investCounts = investCounts;
	}
	public String getMatchDates() {
		return matchDates;
	}
	public void setMatchDates(String matchDates) {
		this.matchDates = matchDates;
	}
	public BigDecimal getjAccedeAccount() {
		return jAccedeAccount;
	}
	public void setjAccedeAccount(BigDecimal jAccedeAccount) {
		this.jAccedeAccount = jAccedeAccount;
	}
	public BigDecimal getJalreadyInvest() {
		return jalreadyInvest;
	}
	public void setJalreadyInvest(BigDecimal jalreadyInvest) {
		this.jalreadyInvest = jalreadyInvest;
	}
	public String getSumAvailableInvestAccount() {
		return sumAvailableInvestAccount;
	}
	public void setSumAvailableInvestAccount(String sumAvailableInvestAccount) {
		this.sumAvailableInvestAccount = sumAvailableInvestAccount;
	}
	public String getSumFrostAccount() {
		return sumFrostAccount;
	}
	public void setSumFrostAccount(String sumFrostAccount) {
		this.sumFrostAccount = sumFrostAccount;
	}
	public String getSumFairValue() {
		return sumFairValue;
	}
	public void setSumFairValue(String sumFairValue) {
		this.sumFairValue = sumFairValue;
	}
}
