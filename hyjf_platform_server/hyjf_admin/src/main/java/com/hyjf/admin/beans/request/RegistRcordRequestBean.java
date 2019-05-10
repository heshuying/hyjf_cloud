package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version RegistRcordRequestBean, v0.1 2018/4/11 12:49
 */
public class RegistRcordRequestBean extends BasePage {
	//用户名
	@ApiModelProperty(value = "用户名")
	private String userName;
	//手机号
	@ApiModelProperty(value = "手机号")
	private String mobile;
	//推荐人
	@ApiModelProperty(value = "推荐人")
	private String recommendName;
	//注册平台
	@ApiModelProperty(value = "注册平台")
	private String registPlat;
	//注册时间（开始）
	@ApiModelProperty(value = "注册时间（开始）")
	private String regTimeStart;
	//注册时间（结束）
	@ApiModelProperty(value = "注册时间（结束）")
	private String regTimeEnd;
	//用户Id
	@ApiModelProperty(value = "userId")
	private String userId;
	//注册渠道Id
	@ApiModelProperty(value = "sourceId")
	private String sourceId;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}

