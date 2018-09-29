package com.hyjf.admin.service.impl;


import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.AdminService;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @package com.hyjf.admin.maintenance.Admin
 * @author dongzeshan
 * @date 2018/09/04 17:00
 * @version V1.0  
 */
@Service
public class AdminServiceImpl extends BaseAdminServiceImpl implements AdminService {
	@Autowired
	private AmConfigClient amConfigClient;
	@Override
	public AdminUserResponse search(AdminRequest adminRequest) {
		
		return amConfigClient.adminUserSearch(adminRequest);
	}

	@Override
	public AdminUserResponse moveToInfoAction(AdminRequest adminRequest) {
		return amConfigClient.adminUserMoveToInfoAction(adminRequest);
	}

	@Override
	public AdminUserResponse insertAction(AdminRequest adminRequest) {
		return amConfigClient.adminUserInsertAction(adminRequest);
	}

	@Override
	public AdminUserResponse updateAction(AdminRequest adminRequest) {
		return amConfigClient.adminUserUpdateAction(adminRequest);
	}

	@Override
	public AdminUserResponse deleteRecordAction(AdminRequest adminRequest) {
		return amConfigClient.adminUserDeleteRecordAction(adminRequest);
	}

	@Override
	public AdminUserResponse resetPwdAction(AdminRequest adminRequest) {
		return amConfigClient.adminUserResetPwdAction(adminRequest);
	}

	@Override
	public AdminUserResponse checkAction(AdminRequest adminRequest) {
		return amConfigClient.adminUsercCheckAction(adminRequest);
	}

	@Override
	public TreeResponse infoAction(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminSystemResponse insertAction(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminSystemResponse getuser(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminSystemResponse deleteRecordAction(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminSystemResponse moveToAuthAction(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminSystemResponse updateMenuPermissionsAction(AdminMenuRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse search(AdminRoleRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse moveToInfoAction(AdminRoleRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse insertAction(AdminRoleRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse updateAction(AdminRoleRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse deleteRecordAction(AdminRoleRequest form) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getAdminRoleMenu(String roleId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse modifyPermissionAction(AdminRoleRequest bean) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public AdminRoleResponse checkAction(AdminRoleRequest bean) {
		// TODO 自动生成的方法存根
		return null;
	}
}
