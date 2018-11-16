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

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class PlanInvestCustomizeVO extends BaseVO {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 计划编号
	 */
	private String planNid;
	/**
	 * 计划订单号
	 */
	private String planOrderId;
	/**
	 * 借款标号
	 */
	private String borrowNid;
	/**
	 * 投资/承接订单号
	 */
	private String orderId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 还款方式名称
	 */
	private String borrowStyleName;
	/**
	 * 项目类型名称
	 */
	private String borrowTypeName;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 预期年化
	 */
	private String borrowApr;
	/**
	 * 投资本金
	 */
	private String account;
	/**
	 * 投资状态
	 */
	private String status;
	/**
	 * 持有期限
	 */
	private String holdDays;
	/**
	 * 剩余期限
	 */
	private String surplusDays;
	/**
	 * 公允价值
	 */
	private String fairValue;
	/**
	 * 预期退出时间
	 */
	private String expectExitTime;
	/**
	 * 退出时间
	 */
	private String exitTime;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;

	/**
	 * 到期公允价值
	 */
	private String expireFairValue;

	/**
	 * 债转编号
	 */
	private String creditNid;

	/**
	 * 类型 0直投 1债转
	 */
	private String type;

	/**
	 * 类型 0月标 1天标
	 */
	private String isDay;

	/**
	 * 延期天数
	 */
	private String delayDays;

	/**
	 * 延期利息
	 */
	private String delayInterest;

	/**
	 * 逾期天数
	 */
	private String lateDays;

	/**
	 * 逾期利息
	 */
	private String lateInterest;

	public String getPlanOrderId() {
		return planOrderId;
	}

	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHoldDays() {
		return holdDays;
	}

	public void setHoldDays(String holdDays) {
		this.holdDays = holdDays;
	}

	public String getSurplusDays() {
		return surplusDays;
	}

	public void setSurplusDays(String surplusDays) {
		this.surplusDays = surplusDays;
	}

	public String getFairValue() {
		return fairValue;
	}

	public void setFairValue(String fairValue) {
		this.fairValue = fairValue;
	}

	public String getBorrowTypeName() {
		return borrowTypeName;
	}

	public void setBorrowTypeName(String borrowTypeName) {
		this.borrowTypeName = borrowTypeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getExpectExitTime() {
		return expectExitTime;
	}

	public void setExpectExitTime(String expectExitTime) {
		this.expectExitTime = expectExitTime;
	}

	public String getExitTime() {
		return exitTime;
	}

	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpireFairValue() {
		return expireFairValue;
	}

	public void setExpireFairValue(String expireFairValue) {
		this.expireFairValue = expireFairValue;
	}

	public String getCreditNid() {
		return creditNid;
	}

	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsDay() {
		return isDay;
	}

	public void setIsDay(String isDay) {
		this.isDay = isDay;
	}

	public String getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(String delayDays) {
		this.delayDays = delayDays;
	}

	public String getDelayInterest() {
		return delayInterest;
	}

	public void setDelayInterest(String delayInterest) {
		this.delayInterest = delayInterest;
	}

	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}

	public String getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(String lateInterest) {
		this.lateInterest = lateInterest;
	}

}
