package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;

import java.util.List;


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