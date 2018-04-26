package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version SmsConfigVO, v0.1 2018/4/23 19:58
 */
public class SmsConfigVO extends BaseVO implements Serializable {
	private Integer id;

	private Integer maxIpCount;

	private Integer maxMachineCount;

	private Integer maxBrowserCount;

	private Integer maxPhoneCount;

	private Integer maxIntervalTime;

	private Integer maxValidTime;

	private String noticeToPhone;

	private String noticeToEmail;

	private Integer noticeToTime;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMaxIpCount() {
		return maxIpCount;
	}

	public void setMaxIpCount(Integer maxIpCount) {
		this.maxIpCount = maxIpCount;
	}

	public Integer getMaxMachineCount() {
		return maxMachineCount;
	}

	public void setMaxMachineCount(Integer maxMachineCount) {
		this.maxMachineCount = maxMachineCount;
	}

	public Integer getMaxBrowserCount() {
		return maxBrowserCount;
	}

	public void setMaxBrowserCount(Integer maxBrowserCount) {
		this.maxBrowserCount = maxBrowserCount;
	}

	public Integer getMaxPhoneCount() {
		return maxPhoneCount;
	}

	public void setMaxPhoneCount(Integer maxPhoneCount) {
		this.maxPhoneCount = maxPhoneCount;
	}

	public Integer getMaxIntervalTime() {
		return maxIntervalTime;
	}

	public void setMaxIntervalTime(Integer maxIntervalTime) {
		this.maxIntervalTime = maxIntervalTime;
	}

	public Integer getMaxValidTime() {
		return maxValidTime;
	}

	public void setMaxValidTime(Integer maxValidTime) {
		this.maxValidTime = maxValidTime;
	}

	public String getNoticeToPhone() {
		return noticeToPhone;
	}

	public void setNoticeToPhone(String noticeToPhone) {
		this.noticeToPhone = noticeToPhone == null ? null : noticeToPhone.trim();
	}

	public String getNoticeToEmail() {
		return noticeToEmail;
	}

	public void setNoticeToEmail(String noticeToEmail) {
		this.noticeToEmail = noticeToEmail == null ? null : noticeToEmail.trim();
	}

	public Integer getNoticeToTime() {
		return noticeToTime;
	}

	public void setNoticeToTime(Integer noticeToTime) {
		this.noticeToTime = noticeToTime;
	}

}
