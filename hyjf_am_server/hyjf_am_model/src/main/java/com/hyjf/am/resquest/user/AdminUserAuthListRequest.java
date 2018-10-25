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

package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangqi
 */
@ApiModel(value = "授权明细")
public class AdminUserAuthListRequest extends BasePage {
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

	@ApiModelProperty(value = "自动投资授权过期时间")
	private String autoInvesEndTime;

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

	@ApiModelProperty(value = "同步类型(1自动投资授权,2债转授权)")
	private Integer type;

	//默认为true ,获取全部数据，为false时，获取部分数据
	private boolean limitFlg = false;

	public boolean isLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(boolean limitFlg) {
		this.limitFlg = limitFlg;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}


