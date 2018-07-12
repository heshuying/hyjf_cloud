/**
 * Description:用户详情显示类PO
 * Copyright: Copyright (c)2015
 * Company: 
 * @author: nxl
 */

package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 王坤
 */

public class AdminUserRecommendRequestBean {

	/** 用户id */
	@ApiModelProperty(value = "用户id")
	private String userId;
	/** 推荐人 */
	@ApiModelProperty(value = "推荐人姓名")
	public String recommendName;
	/** 说明 */
	@ApiModelProperty(value = "说明")
	public String remark;
	/** ip */
	@ApiModelProperty(value = "ip")
	public String ip;
	@ApiModelProperty(value = "用户身份证")
	private String idCard;
	@ApiModelProperty(value = "真实姓名")
	private String trueName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
