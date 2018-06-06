/**
 * Description:按照用户名/手机号查询投资明细（直投产品）用实体类
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: 刘彬
 * @version: 1.0
 *           Created at: 2017年7月19日 下午1:50:02
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.callcenter.beans.customizebean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Administrator
 */

public class CallcenterHztInvestCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 34235234325768654L;

	/**
	 * 借款编号 检索条件
	 */
	private String borrowNidSrch;
	/**
	 * 借款标题 检索条件
	 */
	private String borrowNameSrch;
	/**
	 * 用户名 检索条件
	 */
	private String usernameSrch;
	/**
	 * 推荐人 检索条件
	 */
	private String referrerNameSrch;
	/**
	 * 还款方式 检索条件
	 */
	private String borrowStyleSrch;
	/**
	 * 操作平台 检索条件
	 */
	private String clientSrch;
	/**
	 * 渠道 检索条件
	 */
	private String utmIdSrch;
	/**
	 * 投资时间 检索条件
	 */
	private String timeStartSrch;
	/**
	 * 投资时间 检索条件
	 */
	private String timeEndSrch;
	/**
	 * 借款标题
	 */
	private String borrowName;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 推荐人
	 */
	private String referrerName;
	/**
	 * 借款编号
	 */
	private String borrowNid;
	/**
	 * 年化利率
	 */
	private String borrowApr;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 还款方式
	 */
	private String borrowStyleName;
	/**
	 * 投资金额
	 */
	private String account;
	/**
	 * 渠道
	 */
	private String utmSource;
	/**
	 * 操作平台
	 */
	private String operatingDeck;
	/**
	 * 投资时间
	 */
	private String addtime;

	/**
	 * 借款人ID
	 */
	private String userId;

	/**
	 * NID
	 */
	private String nid;
	/**
	 * 是否邮件发送1可以发，0不可以发
	 */
	private String resendMessage;

	/**
	 * 项目类型
	 */
	private String borrowProjectTypeName;

	/**
	 * 投资订单号
	 */
	private String tenderOrderNum;

	/**
	 * 冻结订单号
	 */
	private String freezeOrderNum;

	/**
	 * 投资人用户名
	 */
	private String tenderUsername;

	/**
	 * 投资人用户ID
	 */
	private String tenderUserId;

	/**
	 * 推荐人ID（当前）
	 */
	private String referrerUserId;
	/**
	 * 推荐人ID（投资时）
	 */
	private String tenderReferrerUserId;
	/**
	 * 投资人用户属性（投资时）
	 */
	private String tenderUserAttribute;
	/**
	 * 推荐人用户属性（投资时）
	 */
	private String inviteUserAttribute;
	/**
	 * 推荐人（投资时）
	 */
	private String tenderReferrerUsername;

	/**
	 * 一级分部（投资时）
	 */
	private String departmentLevel1Name;

	/**
	 * 二级分部（投资时）
	 */
	private String departmentLevel2Name;

	/**
	 * 团队（投资时）
	 */
	private String teamName;

	/**
	 * 投资人用户属性（当前）
	 */
	private String tenderUserAttributeNow;

	/**
	 * 投资人所属一级分部（当前）
	 */
	private String tenderRegionName;

	/**
	 * 投资人所属二级分部（当前）
	 */
	private String tenderBranchName;

	/**
	 * 投资人所属团队（当前）
	 */
	private String tenderDepartmentName;

	/**
	 * 推荐人姓名（当前）
	 */
	private String referrerTrueName;

	/**
	 * 推荐人所属一级分部（当前）
	 */
	private String referrerRegionName;

	/**
	 * 推荐人所属二级分部（当前）
	 */
	private String referrerBranchName;

	/**
	 * 推荐人所属团队（当前）
	 */
	private String referrerDepartmentName;
	/**
	 * 投资类别 0手动投标，1或者别的预约投标
	 */
	private String investType;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;
	
	/**
	 * 产品加息收益率
	 */
	private BigDecimal borrowExtraYield;
	
	/**
	 * tenderUserAttribute
	 * 
	 * @return the tenderUserAttribute
	 */

	public String getTenderUserAttribute() {
		return tenderUserAttribute;
	}

	/**
	 * @param tenderUserAttribute
	 *            the tenderUserAttribute to set
	 */

	public void setTenderUserAttribute(String tenderUserAttribute) {
		this.tenderUserAttribute = tenderUserAttribute;
	}

	/**
	 * inviteUserAttribute
	 * 
	 * @return the inviteUserAttribute
	 */

	public String getInviteUserAttribute() {
		return inviteUserAttribute;
	}

	/**
	 * @param inviteUserAttribute
	 *            the inviteUserAttribute to set
	 */

	public void setInviteUserAttribute(String inviteUserAttribute) {
		this.inviteUserAttribute = inviteUserAttribute;
	}

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * borrowProjectTypeName
	 * 
	 * @return the borrowProjectTypeName
	 */

	public String getBorrowProjectTypeName() {
		return borrowProjectTypeName;
	}

	/**
	 * @param borrowProjectTypeName
	 *            the borrowProjectTypeName to set
	 */

	public void setBorrowProjectTypeName(String borrowProjectTypeName) {
		this.borrowProjectTypeName = borrowProjectTypeName;
	}

	/**
	 * tenderOrderNum
	 * 
	 * @return the tenderOrderNum
	 */

	public String getTenderOrderNum() {
		return tenderOrderNum;
	}

	/**
	 * @param tenderOrderNum
	 *            the tenderOrderNum to set
	 */

	public void setTenderOrderNum(String tenderOrderNum) {
		this.tenderOrderNum = tenderOrderNum;
	}

	/**
	 * freezeOrderNum
	 * 
	 * @return the freezeOrderNum
	 */

	public String getFreezeOrderNum() {
		return freezeOrderNum;
	}

	/**
	 * @param freezeOrderNum
	 *            the freezeOrderNum to set
	 */

	public void setFreezeOrderNum(String freezeOrderNum) {
		this.freezeOrderNum = freezeOrderNum;
	}

	/**
	 * tenderUsername
	 * 
	 * @return the tenderUsername
	 */

	public String getTenderUsername() {
		return tenderUsername;
	}

	/**
	 * @param tenderUsername
	 *            the tenderUsername to set
	 */

	public void setTenderUsername(String tenderUsername) {
		this.tenderUsername = tenderUsername;
	}

	/**
	 * tenderUserId
	 * 
	 * @return the tenderUserId
	 */

	public String getTenderUserId() {
		return tenderUserId;
	}

	/**
	 * @param tenderUserId
	 *            the tenderUserId to set
	 */

	public void setTenderUserId(String tenderUserId) {
		this.tenderUserId = tenderUserId;
	}

	/**
	 * referrerUserId
	 * 
	 * @return the referrerUserId
	 */

	public String getReferrerUserId() {
		return referrerUserId;
	}

	/**
	 * @param referrerUserId
	 *            the referrerUserId to set
	 */

	public void setReferrerUserId(String referrerUserId) {
		this.referrerUserId = referrerUserId;
	}

	/**
	 * tenderReferrerUserId
	 * 
	 * @return the tenderReferrerUserId
	 */

	public String getTenderReferrerUserId() {
		return tenderReferrerUserId;
	}

	/**
	 * @param tenderReferrerUserId
	 *            the tenderReferrerUserId to set
	 */

	public void setTenderReferrerUserId(String tenderReferrerUserId) {
		this.tenderReferrerUserId = tenderReferrerUserId;
	}

	/**
	 * tenderReferrerUsername
	 * 
	 * @return the tenderReferrerUsername
	 */

	public String getTenderReferrerUsername() {
		return tenderReferrerUsername;
	}

	/**
	 * @param tenderReferrerUsername
	 *            the tenderReferrerUsername to set
	 */

	public void setTenderReferrerUsername(String tenderReferrerUsername) {
		this.tenderReferrerUsername = tenderReferrerUsername;
	}

	/**
	 * departmentLevel1Name
	 * 
	 * @return the departmentLevel1Name
	 */

	public String getDepartmentLevel1Name() {
		return departmentLevel1Name;
	}

	/**
	 * @param departmentLevel1Name
	 *            the departmentLevel1Name to set
	 */

	public void setDepartmentLevel1Name(String departmentLevel1Name) {
		this.departmentLevel1Name = departmentLevel1Name;
	}

	/**
	 * departmentLevel2Name
	 * 
	 * @return the departmentLevel2Name
	 */

	public String getDepartmentLevel2Name() {
		return departmentLevel2Name;
	}

	/**
	 * @param departmentLevel2Name
	 *            the departmentLevel2Name to set
	 */

	public void setDepartmentLevel2Name(String departmentLevel2Name) {
		this.departmentLevel2Name = departmentLevel2Name;
	}

	/**
	 * teamName
	 * 
	 * @return the teamName
	 */

	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName
	 *            the teamName to set
	 */

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * limitStart
	 * 
	 * @return the limitStart
	 */

	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * borrowNidSrch
	 * 
	 * @return the borrowNidSrch
	 */

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	/**
	 * @param borrowNidSrch
	 *            the borrowNidSrch to set
	 */

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	/**
	 * borrowNameSrch
	 * 
	 * @return the borrowNameSrch
	 */

	public String getBorrowNameSrch() {
		return borrowNameSrch;
	}

	/**
	 * @param borrowNameSrch
	 *            the borrowNameSrch to set
	 */

	public void setBorrowNameSrch(String borrowNameSrch) {
		this.borrowNameSrch = borrowNameSrch;
	}

	/**
	 * usernameSrch
	 * 
	 * @return the usernameSrch
	 */

	public String getUsernameSrch() {
		return usernameSrch;
	}

	/**
	 * @param usernameSrch
	 *            the usernameSrch to set
	 */

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}

	/**
	 * referrerNameSrch
	 * 
	 * @return the referrerNameSrch
	 */

	public String getReferrerNameSrch() {
		return referrerNameSrch;
	}

	/**
	 * @param referrerNameSrch
	 *            the referrerNameSrch to set
	 */

	public void setReferrerNameSrch(String referrerNameSrch) {
		this.referrerNameSrch = referrerNameSrch;
	}

	/**
	 * borrowStyleSrch
	 * 
	 * @return the borrowStyleSrch
	 */

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	/**
	 * @param borrowStyleSrch
	 *            the borrowStyleSrch to set
	 */

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	/**
	 * clientSrch
	 * 
	 * @return the clientSrch
	 */

	public String getClientSrch() {
		return clientSrch;
	}

	/**
	 * @param clientSrch
	 *            the clientSrch to set
	 */

	public void setClientSrch(String clientSrch) {
		this.clientSrch = clientSrch;
	}

	/**
	 * utmIdSrch
	 * 
	 * @return the utmIdSrch
	 */

	public String getUtmIdSrch() {
		return utmIdSrch;
	}

	/**
	 * @param utmIdSrch
	 *            the utmIdSrch to set
	 */

	public void setUtmIdSrch(String utmIdSrch) {
		this.utmIdSrch = utmIdSrch;
	}

	/**
	 * timeStartSrch
	 * 
	 * @return the timeStartSrch
	 */

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	/**
	 * @param timeStartSrch
	 *            the timeStartSrch to set
	 */

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	/**
	 * timeEndSrch
	 * 
	 * @return the timeEndSrch
	 */

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	/**
	 * @param timeEndSrch
	 *            the timeEndSrch to set
	 */

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

	/**
	 * borrowName
	 * 
	 * @return the borrowName
	 */

	public String getBorrowName() {
		return borrowName;
	}

	/**
	 * @param borrowName
	 *            the borrowName to set
	 */

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	/**
	 * username
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * referrerName
	 * 
	 * @return the referrerName
	 */

	public String getReferrerName() {
		return referrerName;
	}

	/**
	 * @param referrerName
	 *            the referrerName to set
	 */

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	/**
	 * borrowNid
	 * 
	 * @return the borrowNid
	 */

	public String getBorrowNid() {
		return borrowNid;
	}

	/**
	 * @param borrowNid
	 *            the borrowNid to set
	 */

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	/**
	 * borrowApr
	 * 
	 * @return the borrowApr
	 */

	public String getBorrowApr() {
		return borrowApr;
	}

	/**
	 * @param borrowApr
	 *            the borrowApr to set
	 */

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	/**
	 * borrowPeriod
	 * 
	 * @return the borrowPeriod
	 */

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	/**
	 * @param borrowPeriod
	 *            the borrowPeriod to set
	 */

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	/**
	 * borrowStyleName
	 * 
	 * @return the borrowStyleName
	 */

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	/**
	 * @param borrowStyleName
	 *            the borrowStyleName to set
	 */

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * utmSource
	 * 
	 * @return the utmSource
	 */

	public String getUtmSource() {
		return utmSource;
	}

	/**
	 * @param utmSource
	 *            the utmSource to set
	 */

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	/**
	 * operatingDeck
	 * 
	 * @return the operatingDeck
	 */

	public String getOperatingDeck() {
		return operatingDeck;
	}

	/**
	 * @param operatingDeck
	 *            the operatingDeck to set
	 */

	public void setOperatingDeck(String operatingDeck) {
		this.operatingDeck = operatingDeck;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getResendMessage() {
		return resendMessage;
	}

	public void setResendMessage(String resendMessage) {
		this.resendMessage = resendMessage;
	}

	/**
	 * addtime
	 * 
	 * @return the addtime
	 */

	public String getAddtime() {
		return addtime;
	}

	/**
	 * @param addtime
	 *            the addtime to set
	 */

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTenderUserAttributeNow() {
		return tenderUserAttributeNow;
	}

	public void setTenderUserAttributeNow(String tenderUserAttributeNow) {
		this.tenderUserAttributeNow = tenderUserAttributeNow;
	}

	public String getTenderRegionName() {
		return tenderRegionName;
	}

	public void setTenderRegionName(String tenderRegionName) {
		this.tenderRegionName = tenderRegionName;
	}

	public String getTenderBranchName() {
		return tenderBranchName;
	}

	public void setTenderBranchName(String tenderBranchName) {
		this.tenderBranchName = tenderBranchName;
	}

	public String getTenderDepartmentName() {
		return tenderDepartmentName;
	}

	public void setTenderDepartmentName(String tenderDepartmentName) {
		this.tenderDepartmentName = tenderDepartmentName;
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

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public BigDecimal getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

}
