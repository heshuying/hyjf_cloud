package com.hyjf.am.response.config;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.AdminCustomizeVO;
import com.hyjf.am.vo.admin.AdminRoleVO;
import com.hyjf.am.vo.admin.ROaDepartmentVO;

import java.util.List;

/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 */
public class AdminUserResponse extends AdminResponse<AdminCustomizeVO> {
	
	

    private List<ROaDepartmentVO> departmentList;

    private List<AdminRoleVO> adminRoleList;

	public List<ROaDepartmentVO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<ROaDepartmentVO> departmentList) {
		this.departmentList = departmentList;
	}

	public List<AdminRoleVO> getAdminRoleList() {
		return adminRoleList;
	}

	public void setAdminRoleList(List<AdminRoleVO> adminRoleList) {
		this.adminRoleList = adminRoleList;
	}
    
}
