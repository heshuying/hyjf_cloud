/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author fuqiang
 * @version EventRequestBean, v0.1 2018/7/11 17:46
 */
public class EventRequestBean extends BaseRequest {
	private Integer id;
    @ApiModelProperty(value = "记事时间")
	private String eventTime;
	@ApiModelProperty(value = "记事标题")
	private String title;
	@ApiModelProperty(value = "记事内容")
	private String content;
	@ApiModelProperty(value = "记事年份")
	private Integer eventYear;
	@ApiModelProperty(value = "状态 0:关闭 1:启用")
	private Integer status;

	private String addAdmin;

	private Integer actTime;
	@ApiModelProperty(value = "上传图片路径")
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
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	/** 结束时间 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
