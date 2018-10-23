/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.AdminRoleService;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.admin.UserRoleRequest;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DongZeShan
 * @version AdminRoleServiceImpl.java, v0.1 2018年10月11日 下午4:15:59
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	   @Autowired
	    AmConfigClient amConfigClient;

	@Override
	public AdminRoleResponse search(AdminRoleRequest form) {
		return amConfigClient.search(form);
	}

	@Override
	public AdminRoleResponse moveToInfoAction(AdminRoleRequest form) {
		 
		return amConfigClient.moveToInfoAction(form);
	}

	@Override
	public AdminRoleResponse insertRecord(AdminRoleRequest form) {
		 
		return amConfigClient.insertRecord(form);
	}

	@Override
	public AdminRoleResponse updateRecord(AdminRoleRequest form) {
		 
		return amConfigClient.updateRecord(form);
	}

	@Override
	public AdminRoleResponse deleteRecord(AdminRoleRequest form) {
		 
		return amConfigClient.deleteRecord(form);
	}

	@Override
	public JSONArray getAdminRoleMenu(String roleId) {
		 
		return amConfigClient.getAdminRoleMenu(roleId);
	}

	@Override
	public AdminRoleResponse checkAction(AdminRoleRequest bean) {
		 
		return amConfigClient.checkAction(bean);
	}

	@Override
	public AdminRoleResponse modifyPermissionAction(UserRoleRequest bean) {
		 
		return amConfigClient.modifyPermissionAction(bean);
	}

	@Override
	public JSONArray selectLeftMenuTree(String id) {
		return amConfigClient.selectLeftMenuTree(id);
	}

	@Override
	public AdminSystemResponse insertAction(AdminMenuRequest form) {
 
		return amConfigClient.insertAction(form) ;
	}

	@Override
	public AdminSystemResponse getuser(AdminMenuRequest form) {
 
		return amConfigClient.getuser(form);
	}

	@Override
	public AdminSystemResponse deleteRecordAction(AdminMenuRequest form) {
 
		return amConfigClient.deleteRecordAction(form);
	}

	@Override
	public AdminSystemResponse moveToAuthAction(AdminMenuRequest form) {
 
		return amConfigClient.moveToAuthAction(form);
	}

	@Override
	public AdminSystemResponse updateMenuPermissionsAction(AdminMenuRequest form) {
 
		return amConfigClient.updateMenuPermissionsAction(form);
	}

	@Override
	public List<String> getPermissionId(String string) {
		return amConfigClient.getPermissionId(string);
	}

}
