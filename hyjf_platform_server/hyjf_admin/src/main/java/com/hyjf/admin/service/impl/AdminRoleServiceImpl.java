/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AdminRoleService;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.resquest.admin.UserRoleRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;

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

}
