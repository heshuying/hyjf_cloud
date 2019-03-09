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

package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dongzeshan
 */
@ApiModel(value = "授权明细")
public class AdminUserAuthListVO  extends BaseVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用戶id")
	public String userId;

	@ApiModelProperty(value = "用戶名")
	public String userName;

	@ApiModelProperty(value = "推荐人")
	public String recommendName;

	@ApiModelProperty(value = "手机号")
	public String mobile;

	@ApiModelProperty(value = "自动投标授权状态")
	private String autoInvesStatus;

	@ApiModelProperty(value = "自动预约取现状态")
	private String autoWithdrawStatus;

	@ApiModelProperty(value = "自动无密消费状态")
	private String autoConsumeStatus;

	@ApiModelProperty(value = "自动投标授权状态")
	private String autoCreditStatus;

	@ApiModelProperty(value = "投标授权时间")
	private String autoCreateTime;

	@ApiModelProperty(value = "自动出借授权过期时间")
	private String autoInvesEndTime;

	@ApiModelProperty(value = "自动债转授权过期时间")
	private String autoCreditEndTime;

	@ApiModelProperty(value = "投标订单号")
	private String autoOrderId;

	@ApiModelProperty(value = "授权时间开始时间")
	private String invesAddTimeStart;

	@ApiModelProperty(value = "授权时间结束时间")
	private String invesAddTimeEnd;

	@ApiModelProperty(value = "签约到期日开始时间")
	private String investEndTimeStart;

	@ApiModelProperty(value = "签约到期日结束时间")
	private String investEndTimeEnd;

	private String autoCreditOrderId;

	@ApiModelProperty(value = "自动投标交易金额")
	private String automaticTenderAmount;
			@ApiModelProperty(value = "自动投标总金额")
	private	String totalAmountAutomatic;

	public String getAutomaticTenderAmount() {
		return automaticTenderAmount = "2000000";
	}

	public void setAutomaticTenderAmount(String automaticTenderAmount) {
		this.automaticTenderAmount = automaticTenderAmount;
	}

	public String getTotalAmountAutomatic() {
		return totalAmountAutomatic = "1000000000";
	}

	public void setTotalAmountAutomatic(String totalAmountAutomatic) {
		this.totalAmountAutomatic = totalAmountAutomatic;
	}
	@ApiModelProperty(value = "自动出借单笔最高金额")
	private String invesMaxAmt;

	@ApiModelProperty(value = "自动债转单笔最大金额")
	private String creditMaxAmt;

	public String getInvesMaxAmt() {
		return invesMaxAmt;
	}

	public void setInvesMaxAmt(String invesMaxAmt) {
		this.invesMaxAmt = invesMaxAmt;
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


