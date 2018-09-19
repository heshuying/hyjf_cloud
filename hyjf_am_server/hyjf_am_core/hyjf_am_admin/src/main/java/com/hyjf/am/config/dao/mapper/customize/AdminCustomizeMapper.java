package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.config.dao.model.customize.AdminCustomize;


public interface AdminCustomizeMapper {

	/**
	 * 获取用户列表
	 * 
	 * @param adminSystem
	 * @return
	 */
	List<AdminCustomize> selectAdminList(AdminCustomize adminCustomize);

}