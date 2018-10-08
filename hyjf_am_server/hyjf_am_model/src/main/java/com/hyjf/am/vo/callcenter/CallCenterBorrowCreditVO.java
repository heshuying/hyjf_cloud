package com.hyjf.am.vo.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version CallcenterHztInvestVO, v0.1 2018/6/16 17:22
 */
public class CallCenterBorrowCreditVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �������� �û���
	 */
	private String usernameSrch;

	/**
	 * �������� ծת���
	 */
	private String creditNidSrch;

	/**
	 * �������� ��Ŀ���
	 */
	private String bidNidSrch;

	/**
	 * �������� ת��״̬
	 */
	private String creditStatusSrch;

	/**
	 * �������� ����ʱ�俪ʼ
	 */
	private String timeStartSrch;

	/**
	 * �������� ����ʱ�俪ʼ
	 */
	private String timeEndSrch;

	/**
	 * �������� limitStart
	 */
	private int limitStart = -1;

	/**
	 * �������� limitEnd
	 */
	private int limitEnd = -1;

	/**
	 * ���
	 */
	private String creditId;

	/**
	 * ծת���
	 */
	private String creditNid;

	/**
	 * ��Ŀ���
	 */
	private String bidNid;

	/**
	 * �û���
	 */
	private String username;

	/**
	 * ծȨ����
	 */
	private String creditCapital;

	/**
	 * ת�ñ���
	 */
	private String creditCapitalPrice;

	/**
	 * ������
	 */
	private String creditDiscount;

	/**
	 * ת�ü۸�
	 */
	private String creditPrice;

	/**
	 * ��ת�ý��
	 */
	private String creditCapitalAssigned;

	/**
	 * ת��״̬
	 */
	private String creditStatus;

	/**
	 * ����ʱ��
	 */
	private String addTime;

	/**
	 * ����ʱ��
	 */
	private String repayLastTime;

	/**
	 * ������
	 */
	private String assignNid;

	/**
	 * ������
	 */
	private String creditUsername;

	/**
	 * ת�ñ���
	 */
	private String assignCapital;

	/**
	 * ת�ü۸�
	 */
	private String assignCapitalPrice;

	/**
	 * �Ϲ����
	 */
	private String assignPrice;

	/**
	 * �渶��Ϣ
	 */
	private String assignInterestAdvance;
	/**
	 * �����
	 */
	private String creditFee;
	/**
	 * �Ϲ�ʱ��
	 */
	private String assignPay;
	/**
	 * ״̬����
	 */
	private String creditStatusName;
	/**
	 * ״̬����
	 */
	private String repayStatusName;
	/**
	 * �ͻ��� 0pc 1ios 2Android 3΢��
	 */
	private String client;
	/**
	 * �û���
	 */
	private Integer userId;

	/**
	 * creditStatusName
	 * 
	 * @return the creditStatusName
	 */

	public String getCreditStatusName() {
		return creditStatusName;
	}

	/**
	 * @param creditStatusName
	 *            the creditStatusName to set
	 */

	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}

	/**
	 * assignCapital
	 * 
	 * @return the assignCapital
	 */

	public String getAssignCapital() {
		return assignCapital;
	}

	/**
	 * @param assignCapital
	 *            the assignCapital to set
	 */

	public void setAssignCapital(String assignCapital) {
		this.assignCapital = assignCapital;
	}

	/**
	 * assignCapitalPrice
	 * 
	 * @return the assignCapitalPrice
	 */

	public String getAssignCapitalPrice() {
		return assignCapitalPrice;
	}

	/**
	 * @param assignCapitalPrice
	 *            the assignCapitalPrice to set
	 */

	public void setAssignCapitalPrice(String assignCapitalPrice) {
		this.assignCapitalPrice = assignCapitalPrice;
	}

	/**
	 * assignNid
	 * 
	 * @return the assignNid
	 */

	public String getAssignNid() {
		return assignNid;
	}

	/**
	 * @param assignNid
	 *            the assignNid to set
	 */

	public void setAssignNid(String assignNid) {
		this.assignNid = assignNid;
	}

	/**
	 * creditUsername
	 * 
	 * @return the creditUsername
	 */

	public String getCreditUsername() {
		return creditUsername;
	}

	/**
	 * @param creditUsername
	 *            the creditUsername to set
	 */

	public void setCreditUsername(String creditUsername) {
		this.creditUsername = creditUsername;
	}

	/**
	 * assignPrice
	 * 
	 * @return the assignPrice
	 */

	public String getAssignPrice() {
		return assignPrice;
	}

	/**
	 * @param assignPrice
	 *            the assignPrice to set
	 */

	public void setAssignPrice(String assignPrice) {
		this.assignPrice = assignPrice;
	}

	/**
	 * assignInterestAdvance
	 * 
	 * @return the assignInterestAdvance
	 */

	public String getAssignInterestAdvance() {
		return assignInterestAdvance;
	}

	/**
	 * @param assignInterestAdvance
	 *            the assignInterestAdvance to set
	 */

	public void setAssignInterestAdvance(String assignInterestAdvance) {
		this.assignInterestAdvance = assignInterestAdvance;
	}

	/**
	 * creditFee
	 * 
	 * @return the creditFee
	 */

	public String getCreditFee() {
		return creditFee;
	}

	/**
	 * @param creditFee
	 *            the creditFee to set
	 */

	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
	}

	/**
	 * assignPay
	 * 
	 * @return the assignPay
	 */

	public String getAssignPay() {
		return assignPay;
	}

	/**
	 * @param assignPay
	 *            the assignPay to set
	 */

	public void setAssignPay(String assignPay) {
		this.assignPay = assignPay;
	}

	/**
	 * creditCapitalPrice
	 * 
	 * @return the creditCapitalPrice
	 */

	public String getCreditCapitalPrice() {
		return creditCapitalPrice;
	}

	/**
	 * @param creditCapitalPrice
	 *            the creditCapitalPrice to set
	 */

	public void setCreditCapitalPrice(String creditCapitalPrice) {
		this.creditCapitalPrice = creditCapitalPrice;
	}

	/**
	 * repayLastTime
	 * 
	 * @return the repayLastTime
	 */

	public String getRepayLastTime() {
		return repayLastTime;
	}

	/**
	 * @param repayLastTime
	 *            the repayLastTime to set
	 */

	public void setRepayLastTime(String repayLastTime) {
		this.repayLastTime = repayLastTime;
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
	 * creditNidSrch
	 * 
	 * @return the creditNidSrch
	 */

	public String getCreditNidSrch() {
		return creditNidSrch;
	}

	/**
	 * @param creditNidSrch
	 *            the creditNidSrch to set
	 */

	public void setCreditNidSrch(String creditNidSrch) {
		this.creditNidSrch = creditNidSrch;
	}

	/**
	 * bidNidSrch
	 * 
	 * @return the bidNidSrch
	 */

	public String getBidNidSrch() {
		return bidNidSrch;
	}

	/**
	 * @param bidNidSrch
	 *            the bidNidSrch to set
	 */

	public void setBidNidSrch(String bidNidSrch) {
		this.bidNidSrch = bidNidSrch;
	}

	/**
	 * creditStatusSrch
	 * 
	 * @return the creditStatusSrch
	 */

	public String getCreditStatusSrch() {
		return creditStatusSrch;
	}

	/**
	 * @param creditStatusSrch
	 *            the creditStatusSrch to set
	 */

	public void setCreditStatusSrch(String creditStatusSrch) {
		this.creditStatusSrch = creditStatusSrch;
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
	 * creditId
	 * 
	 * @return the creditId
	 */

	public String getCreditId() {
		return creditId;
	}

	/**
	 * @param creditId
	 *            the creditId to set
	 */

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	/**
	 * creditNid
	 * 
	 * @return the creditNid
	 */

	public String getCreditNid() {
		return creditNid;
	}

	/**
	 * @param creditNid
	 *            the creditNid to set
	 */

	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}

	/**
	 * bidNid
	 * 
	 * @return the bidNid
	 */

	public String getBidNid() {
		return bidNid;
	}

	/**
	 * @param bidNid
	 *            the bidNid to set
	 */

	public void setBidNid(String bidNid) {
		this.bidNid = bidNid;
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
	 * creditCapital
	 * 
	 * @return the creditCapital
	 */

	public String getCreditCapital() {
		return creditCapital;
	}

	/**
	 * @param creditCapital
	 *            the creditCapital to set
	 */

	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}

	/**
	 * creditDiscount
	 * 
	 * @return the creditDiscount
	 */

	public String getCreditDiscount() {
		return creditDiscount;
	}

	/**
	 * @param creditDiscount
	 *            the creditDiscount to set
	 */

	public void setCreditDiscount(String creditDiscount) {
		this.creditDiscount = creditDiscount;
	}

	/**
	 * creditPrice
	 * 
	 * @return the creditPrice
	 */

	public String getCreditPrice() {
		return creditPrice;
	}

	/**
	 * @param creditPrice
	 *            the creditPrice to set
	 */

	public void setCreditPrice(String creditPrice) {
		this.creditPrice = creditPrice;
	}

	/**
	 * creditCapitalAssigned
	 * 
	 * @return the creditCapitalAssigned
	 */

	public String getCreditCapitalAssigned() {
		return creditCapitalAssigned;
	}

	/**
	 * @param creditCapitalAssigned
	 *            the creditCapitalAssigned to set
	 */

	public void setCreditCapitalAssigned(String creditCapitalAssigned) {
		this.creditCapitalAssigned = creditCapitalAssigned;
	}

	/**
	 * creditStatus
	 * 
	 * @return the creditStatus
	 */

	public String getCreditStatus() {
		return creditStatus;
	}

	/**
	 * @param creditStatus
	 *            the creditStatus to set
	 */

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	/**
	 * addTime
	 * 
	 * @return the addTime
	 */

	public String getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getRepayStatusName() {
		return repayStatusName;
	}

	public void setRepayStatusName(String repayStatusName) {
		this.repayStatusName = repayStatusName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
