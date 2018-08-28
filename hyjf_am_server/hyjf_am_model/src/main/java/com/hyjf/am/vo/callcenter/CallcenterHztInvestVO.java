package com.hyjf.am.vo.callcenter;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version CallcenterHztInvestVO, v0.1 2018/6/16 17:22
 */
public class CallcenterHztInvestVO  extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ����� ��������
	 */
	private String borrowNidSrch;
	/**
	 * ������ ��������
	 */
	private String borrowNameSrch;
	/**
	 * �û��� ��������
	 */
	private String usernameSrch;
	/**
	 * �Ƽ��� ��������
	 */
	private String referrerNameSrch;
	/**
	 * ���ʽ ��������
	 */
	private String borrowStyleSrch;
	/**
	 * ����ƽ̨ ��������
	 */
	private String clientSrch;
	/**
	 * ���� ��������
	 */
	private String utmIdSrch;
	/**
	 * Ͷ��ʱ�� ��������
	 */
	private String timeStartSrch;
	/**
	 * Ͷ��ʱ�� ��������
	 */
	private String timeEndSrch;
	/**
	 * ������
	 */
	private String borrowName;
	/**
	 * �û���
	 */
	private String username;
	/**
	 * �Ƽ���
	 */
	private String referrerName;
	/**
	 * �����
	 */
	private String borrowNid;
	/**
	 * �껯����
	 */
	private String borrowApr;
	/**
	 * �������
	 */
	private String borrowPeriod;
	/**
	 * ���ʽ
	 */
	private String borrowStyleName;
	/**
	 * Ͷ�ʽ��
	 */
	private String account;
	/**
	 * ����
	 */
	private String utmSource;
	/**
	 * ����ƽ̨
	 */
	private String operatingDeck;
	/**
	 * Ͷ��ʱ��
	 */
	private String addtime;

	/**
	 * �����ID
	 */
	private String userId;

	/**
	 * NID
	 */
	private String nid;
	/**
	 * �Ƿ��ʼ�����1���Է���0�����Է�
	 */
	private String resendMessage;

	/**
	 * ��Ŀ����
	 */
	private String borrowProjectTypeName;

	/**
	 * Ͷ�ʶ�����
	 */
	private String tenderOrderNum;

	/**
	 * ���ᶩ����
	 */
	private String freezeOrderNum;

	/**
	 * Ͷ�����û���
	 */
	private String tenderUsername;

	/**
	 * Ͷ�����û�ID
	 */
	private String tenderUserId;

	/**
	 * �Ƽ���ID����ǰ��
	 */
	private String referrerUserId;
	/**
	 * �Ƽ���ID��Ͷ��ʱ��
	 */
	private String tenderReferrerUserId;
	/**
	 * Ͷ�����û����ԣ�Ͷ��ʱ��
	 */
	private String tenderUserAttribute;
	/**
	 * �Ƽ����û����ԣ�Ͷ��ʱ��
	 */
	private String inviteUserAttribute;
	/**
	 * �Ƽ��ˣ�Ͷ��ʱ��
	 */
	private String tenderReferrerUsername;

	/**
	 * һ���ֲ���Ͷ��ʱ��
	 */
	private String departmentLevel1Name;

	/**
	 * �����ֲ���Ͷ��ʱ��
	 */
	private String departmentLevel2Name;

	/**
	 * �Ŷӣ�Ͷ��ʱ��
	 */
	private String teamName;

	/**
	 * Ͷ�����û����ԣ���ǰ��
	 */
	private String tenderUserAttributeNow;

	/**
	 * Ͷ��������һ���ֲ�����ǰ��
	 */
	private String tenderRegionName;

	/**
	 * Ͷ�������������ֲ�����ǰ��
	 */
	private String tenderBranchName;

	/**
	 * Ͷ���������Ŷӣ���ǰ��
	 */
	private String tenderDepartmentName;

	/**
	 * �Ƽ�����������ǰ��
	 */
	private String referrerTrueName;

	/**
	 * �Ƽ�������һ���ֲ�����ǰ��
	 */
	private String referrerRegionName;

	/**
	 * �Ƽ������������ֲ�����ǰ��
	 */
	private String referrerBranchName;

	/**
	 * �Ƽ��������Ŷӣ���ǰ��
	 */
	private String referrerDepartmentName;
	/**
	 * Ͷ����� 0�ֶ�Ͷ�꣬1���߱��ԤԼͶ��
	 */
	private String investType;

	/**
	 * �������� limitStart
	 */
	private int limitStart = -1;
	/**
	 * �������� limitEnd
	 */
	private int limitEnd = -1;
	
	/**
	 * ��Ʒ��Ϣ������
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
