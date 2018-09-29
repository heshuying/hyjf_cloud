/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.util.Date;

/**
 * @author fuqiang
 * @version EventsRequest, v0.1 2018/7/12 9:12
 */
public class EventsRequest extends BasePage {
	private Integer id;

	private String eventTime;

	private String title;

	private String content;

	private Integer eventYear;

	private Integer status;

	private String addAdmin;

	private Integer actTime;

	private String thumb;

	private Integer createUserId;

	private Integer updateUserId;

	private Date createTime;

	private Date updateTime;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime == null ? null : eventTime.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Integer getEventYear() {
		return eventYear;
	}

	public void setEventYear(Integer eventYear) {
		this.eventYear = eventYear;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddAdmin() {
		return addAdmin;
	}

	public void setAddAdmin(String addAdmin) {
		this.addAdmin = addAdmin == null ? null : addAdmin.trim();
	}

	public Integer getActTime() {
		return actTime;
	}

	public void setActTime(Integer actTime) {
		this.actTime = actTime;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb == null ? null : thumb.trim();
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
