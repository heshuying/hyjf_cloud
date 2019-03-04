/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author tanyy
 * @version ChannelStatisticsDetail.java, v0.1 2018年7月17日 下午3:15:12
 */
public class ChannelStatisticsDetailRequest extends BasePage implements Serializable{


	/**
	 * 渠道查询
	 */
	@ApiModelProperty(value = "渠道")
	private String sourceIdSrch;
	/**
	 * 用户查询
	 */
	@ApiModelProperty(value = "用户名")
	private String userNameSrch;
	/**
	 * 关键词查询
	 */
	@ApiModelProperty(value = "关键字")
	private String keySrch;
	/**
	 * 用户Id
	 */
	/**
	 * 渠道id
	 */
	private String utmIds;

	private Integer[] userIds;

	private String[] utmIdsSrch;

	/**
	 * 注册时间开始
	 */
	private String startTime;

	/**
	 * 注册时间结束
	 */
	private String endTime;

	protected int limitStart;

	protected int limitEnd;

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

	public Integer[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}

	public String[] getUtmIdsSrch() {
		return utmIdsSrch;
	}

	public void setUtmIdsSrch(String[] utmIdsSrch) {
		this.utmIdsSrch = utmIdsSrch;
	}

	public String getUtmIds() {
		return utmIds;
	}

	public void setUtmIds(String utmIds) {
		this.utmIds = utmIds;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
