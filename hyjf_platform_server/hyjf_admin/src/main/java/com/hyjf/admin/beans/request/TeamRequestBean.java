/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author fuqiang
 * @version TeamRequestBean, v0.1 2018/7/11 16:37
 */
public class TeamRequestBean extends BasePage {
	private Integer id;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "职位")
	private String position;

	private String imgurl;

	private String imgappurl;

	@ApiModelProperty(value = "状态 0:关闭 1:启用")
	private Integer status;

	private Integer order;

	private Integer createTime;

	private Integer updateTime;

	private String info;

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
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
