/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.user.result;

/**
 * 获取微信首页VO对象
 *
 * @author jijun
 * @version hyjf 1.0
 * @see中午12:41
 * @since hyjf 1.0 2018年02月27日
 */
public class UserRegistResult extends BaseResultBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String request;

	private String successUrl;
	
	//登录完成后的sign值
	private String sign;

	// 注册成功后,返回前端用户ID
	private Integer userId;

	/** 积分商城用：用户当前积分 */
	private Integer pointsCurrent;

	public UserRegistResult() {
	}

	public UserRegistResult(String request) {
		super(request);
	}

	public UserRegistResult(String status, String statusDesc) {
		super(status, statusDesc);
	}

	@Override
	public String getRequest() {
		return request;
	}

	@Override
	public void setRequest(String request) {
		this.request = request;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getPointsCurrent() {
		return pointsCurrent;
	}

	public void setPointsCurrent(Integer pointsCurrent) {
		this.pointsCurrent = pointsCurrent;
	}
}
