/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import java.io.Serializable;
import java.util.Date;

import com.hyjf.am.vo.BasePage;

/**
 * @author fuqiang
 * @version TeamRequest, v0.1 2018/7/11 16:54
 */
public class TeamRequest extends BasePage implements Serializable {
	private Integer id;

	private String name;

	private String position;

	private String info;

	private String imgurl;

	private String imgappurl;

	private Integer status;

	private Integer orderId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public String getImgappurl() {
		return imgappurl;
	}

	public void setImgappurl(String imgappurl) {
		this.imgappurl = imgappurl == null ? null : imgappurl.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	private String startTime;
	/** 结束时间 */
	private String endTime;

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
