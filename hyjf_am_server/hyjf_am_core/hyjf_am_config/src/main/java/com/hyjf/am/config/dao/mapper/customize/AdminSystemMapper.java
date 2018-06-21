package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.customize.AdminSystem;


public interface AdminSystemMapper {

	/**
	 * 获取用户的左侧菜单
	 * 
	 * @param adminSystem
	 * @return
	 */
	List<AdminSystem> selectLeftMenuTree(AdminSystem adminSystem);

	/**
	 * 获取用户的菜单权限
	 * 
	 * @param adminSystem
	 * @return
	 */
	List<AdminSystem> getUserPermission(AdminSystem adminSystem);

	/**
	 * 获取用户的基本信息
	 * 
	 * @param adminSystem
	 * @return
	 */
	AdminSystem getUserInfo(AdminSystem adminSystem);
	/**
	 * 更新密码
	 * @param adminSystem
	 * @return
	 */
	Integer updatePassword(AdminSystem adminSystem);
	

}