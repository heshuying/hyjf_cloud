/**
 * Description:银行卡绑定列表前端显示所用PO
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.user.dao.model.customize;

/**
 * @author wangqi
 */

public class AdminUserAuthListCustomize {
	/**
	 * 用戶id
	 */
	public String userId;
	/**
	 * 用戶名
	 */
	public String userName;
	/**
	 * 推荐人
	 */
	public String recommendName;
	/**
	 * 手机号
	 */
	public String mobile;
	/**
	 * 自动投标授权状态
	 */
	private String autoInvesStatus;
	/**
	 * 自动预约取现状态
	 */
	private String autoWithdrawStatus;
	/**
	 * 自动无密消费状态
	 */
	private String autoConsumeStatus;
	/**
	 * 自动投标授权状态
	 */
	private String autoCreditStatus;
	/**
	 * 投标授权时间
	 */
	private String autoCreateTime;
	/**
	 * 自动出借授权过期时间
	 */
	private String autoInvesEndTime;
	/**
	 * 自动债转授权过期时间
	 */
	private String autoCreditEndTime;
	/**
	 * 投标订单号
	 */
	private String autoOrderId;

	// 查询条件
	/**
	 * 授权时间开始时间
	 */
	private String invesAddTimeStart;
	/**
	 * 授权时间结束时间
	 */
	private String invesAddTimeEnd;
	/**
	 * 签约到期日开始时间
	 */
	private String investEndTimeStart;
	/**
	 * 签约到期日结束时间
	 */
	private String investEndTimeEnd;
	/**
	 * 自动出借单笔最高金额
	 */
	private String invesMaxAmt;
	/**
	 * 自动债转单笔最大金额
	 */
	private String creditMaxAmt;

	/**
	 * 自动债转授权订单号
	 */
	private String autoCreditOrderId;

	/**
	 * 构造方法不带参数
	 */
	public AdminUserAuthListCustomize() {
		super();
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
	 * @param userId the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * userName
	 *
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}


	public String getAutoInvesStatus() {
		return autoInvesStatus;
	}

	public void setAutoInvesStatus(String autoInvesStatus) {
		this.autoInvesStatus = autoInvesStatus;
	}


	public String getAutoCreditStatus() {
		return autoCreditStatus;
	}

	public void setAutoCreditStatus(String autoCreditStatus) {
		this.autoCreditStatus = autoCreditStatus;
	}

	public String getAutoWithdrawStatus() {
		return autoWithdrawStatus;
	}

	public void setAutoWithdrawStatus(String autoWithdrawStatus) {
		this.autoWithdrawStatus = autoWithdrawStatus;
	}

	public String getAutoConsumeStatus() {
		return autoConsumeStatus;
	}

	public void setAutoConsumeStatus(String autoConsumeStatus) {
		this.autoConsumeStatus = autoConsumeStatus;
	}


	public String getAutoOrderId() {
		return autoOrderId;
	}

	public void setAutoOrderId(String autoOrderId) {
		this.autoOrderId = autoOrderId;
	}

	public String getAutoCreateTime() {
		return autoCreateTime;
	}

	public void setAutoCreateTime(String autoCreateTime) {
		this.autoCreateTime = autoCreateTime;
	}

	public String getAutoInvesEndTime() {
		return autoInvesEndTime;
	}

	public void setAutoInvesEndTime(String autoInvesEndTime) {
		this.autoInvesEndTime = autoInvesEndTime;
	}

	public String getInvesAddTimeStart() {
		return invesAddTimeStart;
	}

	public void setInvesAddTimeStart(String invesAddTimeStart) {
		this.invesAddTimeStart = invesAddTimeStart;
	}

	public String getInvesAddTimeEnd() {
		return invesAddTimeEnd;
	}

	public void setInvesAddTimeEnd(String invesAddTimeEnd) {
		this.invesAddTimeEnd = invesAddTimeEnd;
	}

	public String getInvestEndTimeStart() {
		return investEndTimeStart;
	}

	public void setInvestEndTimeStart(String investEndTimeStart) {
		this.investEndTimeStart = investEndTimeStart;
	}

	public String getInvestEndTimeEnd() {
		return investEndTimeEnd;
	}

	public void setInvestEndTimeEnd(String investEndTimeEnd) {
		this.investEndTimeEnd = investEndTimeEnd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAutoCreditEndTime() {
		return autoCreditEndTime;
	}

	public void setAutoCreditEndTime(String autoCreditEndTime) {
		this.autoCreditEndTime = autoCreditEndTime;
	}

	public String getInvesMaxAmt() {
		return invesMaxAmt;
	}

	public void setInvesMaxAmt(String invesMaxAmt) {
		this.invesMaxAmt = invesMaxAmt;
	}

	public String getCreditMaxAmt() {
		return creditMaxAmt;
	}

	public void setCreditMaxAmt(String creditMaxAmt) {
		this.creditMaxAmt = creditMaxAmt;
	}

	public String getAutoCreditOrderId() {
		return autoCreditOrderId;
	}

	public void setAutoCreditOrderId(String autoCreditOrderId) {
		this.autoCreditOrderId = autoCreditOrderId;
	}
}


