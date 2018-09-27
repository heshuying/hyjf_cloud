/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin.locked;

import com.hyjf.am.vo.BasePage;

/**
 * @author cui
 * @version LockedeUserListRequest, v0.1 2018/9/21 14:26
 */
public class LockedeUserListRequest extends BasePage {

	// 用户名
	private String username;

	// 手机号
	private String mobile;

	// 最后一次登录失败时间-开始时间
	private String lockTimeStartStr;

	// 最后一次登录失败时间-结束时间
	private String lockTimeEndStr;

	// 是否是前台锁定用户
	private Integer isFront;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLockTimeStartStr() {
		return lockTimeStartStr;
	}

	public void setLockTimeStartStr(String lockTimeStartStr) {
		this.lockTimeStartStr = lockTimeStartStr;
	}

	public String getLockTimeEndStr() {
		return lockTimeEndStr;
	}

	public void setLockTimeEndStr(String lockTimeEndStr) {
		this.lockTimeEndStr = lockTimeEndStr;
	}

	public Integer getIsFront() {
		return isFront;
	}

	public void setIsFront(Integer isFront) {
		this.isFront = isFront;
	}
}
