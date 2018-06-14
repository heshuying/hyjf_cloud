/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

/**
 * @author wangjun
 * @version CallCenterAccountDetailRequest, v0.1 2018/6/13 16:08
 */
public class CallCenterAccountDetailRequest extends BaseVO {
	private int userId;

	private String userName;

	private String referrerName;

    private int limitStart;

	private int limitEnd;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}
