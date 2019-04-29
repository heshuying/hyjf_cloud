package com.hyjf.admin.service.impl;


import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AdminService;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	@Autowired
	private CommonProducer commonProducer;

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
		return null;
	}

	@Override
	public AdminSystemResponse insertAction(AdminMenuRequest form) {
		return null;
	}

	@Override
	public AdminSystemResponse getuser(AdminMenuRequest form) {
		return null;
	}

	@Override
	public AdminSystemResponse deleteRecordAction(AdminMenuRequest form) {
		return null;
	}

	@Override
	public AdminSystemResponse moveToAuthAction(AdminMenuRequest form) {
		return null;
	}

	@Override
	public AdminSystemResponse updateMenuPermissionsAction(AdminMenuRequest form) {
		return null;
	}

	@Override
	public AdminRoleResponse search(AdminRoleRequest form) {
		return null;
	}

	@Override
	public AdminRoleResponse moveToInfoAction(AdminRoleRequest form) {
		return null;
	}

	@Override
	public AdminRoleResponse insertAction(AdminRoleRequest form) {
		return null;
	}

	@Override
	public AdminRoleResponse updateAction(AdminRoleRequest form) {
		return null;
	}

	@Override
	public AdminRoleResponse deleteRecordAction(AdminRoleRequest form) {
		return null;
	}

	@Override
	public String getAdminRoleMenu(String roleId) {
		return null;
	}

	@Override
	public AdminRoleResponse modifyPermissionAction(AdminRoleRequest bean) {
		return null;
	}

	@Override
	public AdminRoleResponse checkAction(AdminRoleRequest bean) {
		return null;
	}

	/**
	 * 当用户被删除或者禁用时，发送MQ处理业务流程配置异常处理
	 */
	@Override
	public void sendAdminUser(Object... adminUserId){
		Map<String,Object> map = new HashMap<>();
		try {
			map.put("adminUserId",adminUserId);
			commonProducer.messageSend(
					new MessageContent(MQConstant.WORKFLOW_MESSAGE_TOPIC, UUID.randomUUID().toString(), map));
		} catch (MQException e) {
			logger.error("app push send error...", e);
		}
	}
}
