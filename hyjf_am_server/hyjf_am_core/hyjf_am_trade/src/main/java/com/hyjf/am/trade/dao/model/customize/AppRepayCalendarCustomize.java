package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author xiasq
 * @version RepayCalendarCustomize, v0.1 2018/1/31 9:04
 */
public class AppRepayCalendarCustomize implements Serializable {

	private static final long serialVersionUID = 1997302274841486176L;
	// 标的编号
	private String borrowNid;
	private String borrowName;
	// 优惠券名称
	private String couponName;
	// 金额
	private String account;
	// 借款期限
	private String period;
	// 回款时间
	private String repayTime;

	private String type;

	private String orderId;

	private String couponType;

	private String assignNid;

	//订单状态
	private Integer orderStatus;

	/** 标的状态值，计划类是空字符串*/
	private String borrowStatus;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
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

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
}
