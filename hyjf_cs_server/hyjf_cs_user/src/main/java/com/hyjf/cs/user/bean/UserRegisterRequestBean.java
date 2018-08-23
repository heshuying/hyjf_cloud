package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户注册请求Bean
 *
 * @author zhangqingqing
 *
 */
public class UserRegisterRequestBean extends BaseBean {

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "推荐人")
	private String recommended;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}
}
