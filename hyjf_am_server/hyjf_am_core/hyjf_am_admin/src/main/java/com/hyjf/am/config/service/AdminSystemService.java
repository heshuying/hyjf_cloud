package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.auto.ConfigApplicant;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.response.config.ConfigApplicantResponse;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;

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

	public Boolean updatePassword(String username, String md5Code);

	/**
	 * 修改项目申请人配置
	 * @param request
	 * @return
	 */
	Integer updateApplicantConfigList(ConfigApplicantRequest request);

	/**
	 * 添加项目申请人配置
	 * @param request
	 * @return
	 */
	Integer addApplicantConfigList(ConfigApplicantRequest request);

	/**
	 * 获取单个项目申请人配置
	 * @return
	 */
	ConfigApplicant findConfigApplicant(ConfigApplicantRequest request);

	/**
	 * 获取项目申请人列表
	 * @param request
	 * @return
	 */
	List<ConfigApplicant> getApplicantConfigList(ConfigApplicantRequest request, Integer limitStart, Integer limitEnd);

	/**
	 * 获取项目申请人条数
	 * @param request
	 * @return
	 */
	Integer countApplicantConfigList(ConfigApplicantRequest request);
}
