package com.hyjf.am.config.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.resquest.config.AdminMenuRequest;


public interface AdminMenuService {

	/**
	 * 获取菜单列表
	 *
	 * @return
	 */
	public  List<Tree> getRecordList(AdminMenuRequest form);

	/**
	 * 获取单个菜单
	 *
	 * @return
	 */
	public AdminCustomize getRecord(Integer id);

	/**
	 * 根据主键判断菜单数据是否存在
	 *
	 * @return
	 */
	public boolean isExistsRecord(Integer id);

	/**
	 * 菜单插入
	 *
	 * @param record
	 */
	public void insertRecord(AdminMenuRequest record);

	/**
	 * 菜单更新
	 *
	 * @param record
	 */
	public void updateRecord(AdminMenuRequest record);

	/**
	 * 菜单删除
	 *
	 * @param ids
	 */
	public void deleteRecord(List<String> ids,int adminId);

    /**
     * 取得菜单权限列表
     *
     * @param ids
     */
    public List<AdminSystem> getMenuPermissionsList(String menuUuid);

    /**
     * 菜单权限更新
     *
     * @param record
     */
    public void updateMenuPermissions(AdminMenuRequest record);
}
