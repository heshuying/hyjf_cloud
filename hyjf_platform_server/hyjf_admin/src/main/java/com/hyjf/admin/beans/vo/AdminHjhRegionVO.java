/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AdminHjhRegionVO.java, v0.1 2018年7月21日 下午3:25:53
 */
public class AdminHjhRegionVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键")
	private Integer id;
	@ApiModelProperty(value = "计划编号")
    private String planNid;
	@ApiModelProperty(value = "计划名称")
    private String planName;
	@ApiModelProperty(value = "计划专区添加时间")
    private Integer configAddTime;
	@ApiModelProperty(value = "计划专区状态 0：停用 1：启用")
    private Integer configStatus;
	@ApiModelProperty(value = "创建人id")
    private Integer createUser;
	@ApiModelProperty(value = "创建时间")
    private Integer createTime;
	@ApiModelProperty(value = "更新人id")
    private Integer updateUser;
	@ApiModelProperty(value = "更新时间")
    private Integer updateTime;
	@ApiModelProperty(value = "删除标识")
    private Integer delFlg;
	@ApiModelProperty(value = "计划专区添加时间")
	private String addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getConfigAddTime() {
		return configAddTime;
	}

	public void setConfigAddTime(Integer configAddTime) {
		this.configAddTime = configAddTime;
	}

	public Integer getConfigStatus() {
		return configStatus;
	}

	public void setConfigStatus(Integer configStatus) {
		this.configStatus = configStatus;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
}
