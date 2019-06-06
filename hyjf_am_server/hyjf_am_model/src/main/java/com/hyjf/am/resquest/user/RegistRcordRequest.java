package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

/**
 * @author nxl
 * @version RegisterUserRequest, v0.1 2018/4/11 12:49
 */
public class RegistRcordRequest extends BasePage{
	//用户Id
	private String userId;
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
	//默认为true ,获取全部数据，为false时，获取部分数据
	private boolean limitFlg = false;
	//渠道ID
	private String sourceId;
	//渠道名称
	private String sourceName;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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

	public boolean isLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(boolean limitFlg) {
		this.limitFlg = limitFlg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

