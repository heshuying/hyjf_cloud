/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.util.Date;

/**
 * @author wangjun
 * @version CallCenterUserInfoRequest, v0.1 2018/6/11 17:52
 */
public class CallCenterUserInfoRequest extends BaseVO  {
	private String userName;

	private String mobile;

	private Integer limitStart;

	private Integer limitSize;

	private String flag;
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
	public Integer getLimitSize() {
		return limitSize;
	}
	public void setLimitSize(Integer limitSize) {
		this.limitSize = limitSize;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
