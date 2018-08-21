/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version HjhAllocationEngineVO.java, v0.1 2018年7月4日 上午11:12:25
 */
public class HjhAllocationEngineVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String planNid;

    private String planName;

    private Integer configAddTime;

    private Integer configStatus;

    private Integer labelId;

    private String labelName;

    private Integer addTime;

    private Integer labelSort;

    private Integer transferTimeSort;

    private Integer transferTimeSortPriority;

    private Integer aprSort;

    private Integer aprSortPriority;

    private Integer actulPaySort;

    private Integer actulPaySortPriority;

    private Integer investProgressSort;

    private Integer investProgressSortPriority;

    private Integer labelStatus;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private Integer updateTime;

    private Integer delFlg;
    
    private String addTimeString;

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

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getLabelSort() {
		return labelSort;
	}

	public void setLabelSort(Integer labelSort) {
		this.labelSort = labelSort;
	}

	public Integer getTransferTimeSort() {
		return transferTimeSort;
	}

	public void setTransferTimeSort(Integer transferTimeSort) {
		this.transferTimeSort = transferTimeSort;
	}

	public Integer getTransferTimeSortPriority() {
		return transferTimeSortPriority;
	}

	public void setTransferTimeSortPriority(Integer transferTimeSortPriority) {
		this.transferTimeSortPriority = transferTimeSortPriority;
	}

	public Integer getAprSort() {
		return aprSort;
	}

	public void setAprSort(Integer aprSort) {
		this.aprSort = aprSort;
	}

	public Integer getAprSortPriority() {
		return aprSortPriority;
	}

	public void setAprSortPriority(Integer aprSortPriority) {
		this.aprSortPriority = aprSortPriority;
	}

	public Integer getActulPaySort() {
		return actulPaySort;
	}

	public void setActulPaySort(Integer actulPaySort) {
		this.actulPaySort = actulPaySort;
	}

	public Integer getActulPaySortPriority() {
		return actulPaySortPriority;
	}

	public void setActulPaySortPriority(Integer actulPaySortPriority) {
		this.actulPaySortPriority = actulPaySortPriority;
	}

	public Integer getInvestProgressSort() {
		return investProgressSort;
	}

	public void setInvestProgressSort(Integer investProgressSort) {
		this.investProgressSort = investProgressSort;
	}

	public Integer getInvestProgressSortPriority() {
		return investProgressSortPriority;
	}

	public void setInvestProgressSortPriority(Integer investProgressSortPriority) {
		this.investProgressSortPriority = investProgressSortPriority;
	}

	public Integer getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(Integer labelStatus) {
		this.labelStatus = labelStatus;
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

	public String getAddTimeString() {
		return addTimeString;
	}

	public void setAddTimeString(String addTimeString) {
		this.addTimeString = addTimeString;
	}
}
