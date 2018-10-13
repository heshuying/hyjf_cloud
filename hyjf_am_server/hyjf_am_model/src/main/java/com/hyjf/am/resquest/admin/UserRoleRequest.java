package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

/**
 * @author dongzeshan
 * @version UserRoleRequest, v0.1 2018/9/4 10:46
 */

public class    UserRoleRequest extends BasePage {
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态
     */
    private Integer status;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色id
     */
    private Integer id;

    /**
     *角色菜单权限列表
     */
    private String [] permList;


    /**
     *用户id
     */
    private Integer userId;

    /**
     * 路径
     */
    private String path;

    /**
     * 角色id
     */
    private Integer roleId;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String[] getPermList() {
		return permList;
	}

	public void setPermList(String[] permList) {
		this.permList = permList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
    
}
