package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;


public interface AdminCustomizeMapper {

	/**
	 * 获取用户列表
	 * 
	 * @param adminSystem
	 * @return
	 */
	List<AdminCustomize> selectAdminList(AdminCustomize adminCustomize);

	/**
	 * 根据用户名获取用户
	 * @param userName
	 * @return
	 */
	List<Admin> selectByUsername(String userName);
}