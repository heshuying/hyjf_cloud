package com.hyjf.am.resquest.user;

/**
 * @author nxl
 * 用户详情显示类PO
 */

public class AdminUserRecommendRequest{

	/** 用户id */
	private String userId;
	/** 用户名 */
	private String userName;
	/** 推荐人 */
	private String recommendName;
	/** 说明 */
	private String remark;
	/** ip */
	private String ip;
	//当前登陆用户id
	private String loginUserId;
	//当前登陆用户名
	private String loginUserName;
	//身份证号
	private String idCard;
	//真是姓名
	private String trueName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
}
