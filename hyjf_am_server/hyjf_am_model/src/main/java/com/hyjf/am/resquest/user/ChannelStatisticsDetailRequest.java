/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author tanyy
 * @version ChannelStatisticsDetail.java, v0.1 2018年7月17日 下午3:15:12
 */
public class ChannelStatisticsDetailRequest extends BasePage implements Serializable{


	/**
	 * 渠道查询
	 */
	private String sourceIdSrch;
	/**
	 * 用户查询
	 */
	private String userNameSrch;
	/**
	 * 关键词查询
	 */
	private String keySrch;

	public String getSourceIdSrch() {
		return sourceIdSrch;
	}

	public void setSourceIdSrch(String sourceIdSrch) {
		this.sourceIdSrch = sourceIdSrch;
	}

	public String getUserNameSrch() {
		return userNameSrch;
	}

	public void setUserNameSrch(String userNameSrch) {
		this.userNameSrch = userNameSrch;
	}

	public String getKeySrch() {
		return keySrch;
	}

	public void setKeySrch(String keySrch) {
		this.keySrch = keySrch;
	}
}
