package com.hyjf.am.config.service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissions;
import com.hyjf.am.config.dao.model.customize.AdminRoleCustomize;
import com.hyjf.am.resquest.config.AdminRoleRequest;

import java.util.List;

public interface AdminRoleService {

	 /**
     * 获取角色数
     * 
     * @param AdminRole
     * @param limitStart
     * @param limitEnd
     * @return
     * @author Administrator
     */
    public long getRecordCount(AdminRoleRequest AdminRole);
    
	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<AdminRole> getRecordList(AdminRoleRequest adminRole, int limitStart, int limitEnd);

	/**
	 * 获取单个角色维护
	 * 
	 * @return
	 */
	public AdminRole getRecord(Integer id);

	/**
	 * 根据主键判断角色维护中数据是否存在
	 * 
	 * @return
	 */
	public boolean isExistsRecord(Integer id);

	/**
	 * 角色维护插入
	 * 
	 * @param record
	 */
	public void insertRecord(AdminRoleRequest record);

	/**
	 * 角色维护更新
	 * 
	 * @param record
	 */
	public void updateRecord(AdminRoleRequest record);

	/**
	 * 角色维护删除
	 * 
	 * @param record
	 */
	public void deleteRecord(List<Integer> ids,int adminId);

    /**
     * 获取角色菜单信息
     * 
     * @return
     */
    public JSONArray getAdminRoleMenu(AdminRoleCustomize adminRoleCustomize);
    
    /**
     * 插入或更新[角色菜单权限表]数据
     * 
     * @param record
     */
    public int updatePermission(Integer roleId, List<AdminRoleMenuPermissions> record,int adminId);

    /**
     * 检查角色名称唯一性
     * 
     * @param id
     * @param username
     */
    public int countRoleByname(Integer id, String roleName);
}
