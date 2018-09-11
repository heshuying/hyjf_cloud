package com.hyjf.am.resquest.config;

import java.util.List;

import com.hyjf.am.vo.admin.AdminRoleMenuPermissionsVO;
import com.hyjf.am.vo.admin.AdminRoleVO;


/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class AdminRoleRequest extends AdminRoleVO {

	  /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;


    private String roleId;

    private List<AdminRoleMenuPermissionsVO> permList;
    private List<Integer> ids;

    // 查询用变量
    /** 角色名称 */
    private String roleNameSrch;
    /** 角色状态 */
    private int stateSrch;
    private int stateSrchOn;
    private int stateSrchOff;

   

   

  



	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }



    public String getRoleNameSrch() {
        return roleNameSrch;
    }

    public void setRoleNameSrch(String roleNameSrch) {
        this.roleNameSrch = roleNameSrch;
    }

    public int getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(int stateSrch) {
        this.stateSrch = stateSrch;
    }

    public int getStateSrchOn() {
        return stateSrchOn;
    }

    public void setStateSrchOn(int stateSrchOn) {
        this.stateSrchOn = stateSrchOn;
    }

    public int getStateSrchOff() {
        return stateSrchOff;
    }

    public void setStateSrchOff(int stateSrchOff) {
        this.stateSrchOff = stateSrchOff;
    }

    public List<AdminRoleMenuPermissionsVO> getPermList() {
        return permList;
    }

    public void setPermList(List<AdminRoleMenuPermissionsVO> permList) {
        this.permList = permList;
    }
	private int adminId;

	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	
}
