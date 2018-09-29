package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.AdminRoleCustomize;

import java.util.List;


public interface AdminRoleCustomizeMapper {

	/**
	 * 获取菜单
	 * 
	 * @param adminSystem
	 * @return
	 */
	List<AdminRoleCustomize> selectRoleMenu(AdminRoleCustomize adminRoleCustomize);
	
    /**
     * 获取权限菜单关联数据
     * 
     * @param adminSystem
     * @return
     */
    List<AdminRoleCustomize> selectRoleMenuPermissions(AdminRoleCustomize adminSystem);

}