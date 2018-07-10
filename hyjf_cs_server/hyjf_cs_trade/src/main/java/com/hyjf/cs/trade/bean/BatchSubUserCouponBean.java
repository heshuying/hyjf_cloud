package com.hyjf.cs.trade.bean;

import java.io.Serializable;
import java.util.List;

public class BatchSubUserCouponBean implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8366034835219899415L;

	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 活动编号id
	 */
	private String activityId;
	/**
	 * 优惠券编号
	 */
	private List<String> couponCode;
	
	
	/**
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
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	/**
	 * @return the couponCode
	 */
	public List<String> getCouponCode() {
		return couponCode;
	}
	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(List<String> couponCode) {
		this.couponCode = couponCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
    
}
