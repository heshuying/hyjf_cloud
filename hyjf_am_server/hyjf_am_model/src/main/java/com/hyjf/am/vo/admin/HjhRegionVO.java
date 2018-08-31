/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version HjhRegionVO.java, v0.1 2018年7月3日 下午12:44:28
 */
public class HjhRegionVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String planNid;

    private String planName;

    private Integer configAddTime;

    private Integer configStatus;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private Integer updateTime;

    private Integer delFlg;
    
    private String addTime;
    
    private String addConfigTime;

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

	public String getAddConfigTime() {
		return addConfigTime;
	}

	public void setAddConfigTime(String addConfigTime) {
		this.addConfigTime = addConfigTime;
	}
}
