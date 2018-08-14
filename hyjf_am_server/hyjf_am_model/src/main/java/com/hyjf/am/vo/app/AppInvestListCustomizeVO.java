/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class AppInvestListCustomizeVO extends BaseVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1388457434687484420L;
	// 项目id
	private String borrowNid;
	// 项目名称
	private String borrowName;
	// 年化收益
	private String borrowApr;
	// 项目期限
	private String period;
	// 投资金额
	private String account;
	// 项目进度
	private String borrowSchedule;
	// 项目详情url
	private String borrowUrl = "";
	// 优惠券投资时的优惠券类型，费优惠券投资则为空字符串
	private String label;
	// RTB
	private String projectType;
	// 投资时间
	private int addTime;

	// 投资订单号
	private String orderId;

	private String couponType;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}

	/**
	 * 构造方法
	 */
	public AppInvestListCustomizeVO() {
		super();
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getBorrowSchedule() {
		return borrowSchedule;
	}

	public void setBorrowSchedule(String borrowSchedule) {
		this.borrowSchedule = borrowSchedule;
	}

	public String getBorrowUrl() {
		return borrowUrl;
	}

	public void setBorrowUrl(String borrowUrl) {
		this.borrowUrl = borrowUrl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
}
