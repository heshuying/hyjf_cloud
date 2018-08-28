package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * 员工信息
 * @author jijun 20180622
 */
public class EmployeeCustomize implements Serializable {

	/**
	 * serialVersionUID:TODO  变量 
	 */
		
	private static final long serialVersionUID = -6196389515034236590L;
	/**
     * 员工ID
     */
	private Integer userId;	
	/**
     * 员工名
     */
	private String userName;
	/**
     * 大区ID
     */
	private Integer regionId;	
	/**
     * 大区
     */
	private String regionName;
	/**
     * 分公司ID
     */
	private Integer branchId;	
	/**
     * 
     */
	private String branchName;
	/**
     * 部门ID
     */
	private Integer departmentId;	
	/**
     * 
     */
	private String departmentName;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}




	