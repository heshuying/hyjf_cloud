/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import java.io.Serializable;

/**
 * @author libin
 * @version PlanListCustomizeRequest.java, v0.1 2018年7月6日 下午1:06:17
 */
public class PlanListCustomizeRequest implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 检索条件 计划编号
	 */
	private String planNidSrch;
	/**
	 * 检索条件 计划名称
	 */
	private String planNameSrch;
	/**
	 * 检索条件 锁定期
	 */
	private Integer lockPeriodSrch;
	/**
	 * 检索条件 投资状态
	 */
	private Integer planStatusSrch;
	/**
	 * 检索条件 显示状态
	 */
	private Integer planDisplayStatusSrch;
	/**
	 * 检索条件 还款方式
	 */
	private String borrowStyleSrch;
	/**
	 * 检索条件 添加时间开始
	 */
	private Integer addTimeStart;
	/**
	 * 检索条件 添加时间结束
	 */
	private Integer addTimeEnd;
	public String getPlanNidSrch() {
		return planNidSrch;
	}
	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}
	public String getPlanNameSrch() {
		return planNameSrch;
	}
	public void setPlanNameSrch(String planNameSrch) {
		this.planNameSrch = planNameSrch;
	}
	public Integer getLockPeriodSrch() {
		return lockPeriodSrch;
	}
	public void setLockPeriodSrch(Integer lockPeriodSrch) {
		this.lockPeriodSrch = lockPeriodSrch;
	}
	public Integer getPlanStatusSrch() {
		return planStatusSrch;
	}
	public void setPlanStatusSrch(Integer planStatusSrch) {
		this.planStatusSrch = planStatusSrch;
	}
	public Integer getPlanDisplayStatusSrch() {
		return planDisplayStatusSrch;
	}
	public void setPlanDisplayStatusSrch(Integer planDisplayStatusSrch) {
		this.planDisplayStatusSrch = planDisplayStatusSrch;
	}
	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}
	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}
	public Integer getAddTimeStart() {
		return addTimeStart;
	}
	public void setAddTimeStart(Integer addTimeStart) {
		this.addTimeStart = addTimeStart;
	}
	public Integer getAddTimeEnd() {
		return addTimeEnd;
	}
	public void setAddTimeEnd(Integer addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}
}
