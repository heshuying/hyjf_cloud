package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.AdminSystem;

import java.util.List;

/**
 * @author by dongzeshan on 2018/7/20.
 */
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
	 * 根据用户id查询用户
	 * @auth sunpeikai
	 * @param id 登录用户id
	 * @return
	 */
	AdminSystem getUserInfoById(Integer id);

	/**
	 * 更新密码
	 * @param adminSystem
	 * @return
	 */
	Integer updatePassword(AdminSystem adminSystem);
	

}