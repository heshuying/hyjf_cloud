package com.hyjf.am.resquest.user;

/**
 * @author nxl
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class RegistRcordRequest {
	//用户名
	private String userName;
	//手机号
	private String mobile;
	//推荐人
	private String recommendName;
	//注册平台
	private String registPlat;
	//注册时间（开始）
	private String regTimeStart;
	//注册时间（结束）
	private String regTimeEnd;

	public int limit;

	private int paginatorPage = 1;
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}
	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getRegistPlat() {
		return registPlat;
	}

	public void setRegistPlat(String registPlat) {
		this.registPlat = registPlat;
	}

	public String getRegTimeStart() {
		return regTimeStart;
	}

	public void setRegTimeStart(String regTimeStart) {
		this.regTimeStart = regTimeStart;
	}

	public String getRegTimeEnd() {
		return regTimeEnd;
	}

	public void setRegTimeEnd(String regTimeEnd) {
		this.regTimeEnd = regTimeEnd;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}

