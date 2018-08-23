package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 员工离职信息
 * @author nxl 20180822
 */
public class AdminEmployeeLeaveCustomizeVO extends BaseVO implements Serializable {

	/** 用户id */
	private String userId;
	/** oa的用户id */
	private String oaUserId;
	/** 入职时间 */
	private String entryDate;
	/** 薪资截止日 */
	private String endTime;
	/** 部门1id */
	private String departId1;
	/** 部门1名称 */
	private String departName1;
	/** 部门2id */
	private String departId2;
	/** 部门2名称 */
	private String departName2;
	/** 部门3id */
	private String departId3;
	/** 部门3名称 */
	private String departName3;
	/** 员工类型 */
	private String staffType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOaUserId() {
		return oaUserId;
	}

	public void setOaUserId(String oaUserId) {
		this.oaUserId = oaUserId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDepartId1() {
		return departId1;
	}

	public void setDepartId1(String departId1) {
		this.departId1 = departId1;
	}

	public String getDepartName1() {
		return departName1;
	}

	public void setDepartName1(String departName1) {
		this.departName1 = departName1;
	}

	public String getDepartId2() {
		return departId2;
	}

	public void setDepartId2(String departId2) {
		this.departId2 = departId2;
	}

	public String getDepartName2() {
		return departName2;
	}

	public void setDepartName2(String departName2) {
		this.departName2 = departName2;
	}

	public String getDepartId3() {
		return departId3;
	}

	public void setDepartId3(String departId3) {
		this.departId3 = departId3;
	}

	public String getDepartName3() {
		return departName3;
	}

	public void setDepartName3(String departName3) {
		this.departName3 = departName3;
	}

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

}




	