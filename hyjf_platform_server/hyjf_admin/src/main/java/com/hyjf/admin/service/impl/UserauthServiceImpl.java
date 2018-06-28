/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.UserauthClient;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;

/**
 * @author DongZeShan
 * @version UserauthService.java, v0.1 2018年6月27日 下午2:19:30
 */
@Service
public class UserauthServiceImpl  implements UserauthService{
	@Autowired
	private UserauthClient userauthClient;
	@Override
	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest) {
		
		return userauthClient.userauthlist(adminUserAuthListRequest);
	}

	@Override
	public AdminUserAuthListResponse cancelInvestAuth(int userId, String ordId) {
		
		return userauthClient.cancelInvestAuth(userId, ordId);
	}

	@Override
	public AdminUserAuthListResponse cancelCreditAuth(int userId, String ordId) {
		return userauthClient.cancelCreditAuth(userId, ordId);
	}

	@Override
	public HjhAccedeResponse canCancelAuth(Integer userId) {
		return userauthClient.canCancelAuth(userId);
	}

	@Override
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest) {
		return userauthClient.userauthLoglist(adminUserAuthListRequest);
	}


}
