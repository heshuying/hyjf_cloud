/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

/**
 * @author wangjun
 * @version CallCenterBaseRequest, v0.1 2018/6/11 17:52
 */
public class CallCenterBaseRequest extends BaseVO {
	private Integer userId;

	private String userName;

	private String mobile;

    private Integer limitStart;

	private Integer limitEnd;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}
}
