package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;

import java.util.List;



public interface AdminSystemService {
	/**
	 *获取菜单
	 */
	public List<AdminSystem> selectLeftMenuTree(AdminSystem adminSystem);

	/**
	 * 获取权限
	 */
	public List<AdminSystem> getUserPermission(AdminSystem adminSystem);
	/**
	 * 获取用户信息
	 */
	public AdminSystem getUserInfo(AdminSystem adminSystem);
	/**
	 * 获取菜单2
	 */
	public List<Tree> selectLeftMenuTree2(String id);
	/**
	 * 获取用户信息by userid
	 */
	public AdminSystem getUserInfoByUserId(Integer userId);
	/**
	 * 验证项目申请人是否存在
	 */
	public int isExistsApplicant(String applicant);
	/**
	 * 通过username获取用户详细信息
	 */
	public Admin getUserInfoAll(AdminSystem adminSystem);
}
