/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;


import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DongZeShan
 * @version UserauthService.java, v0.1 2018年6月27日 下午2:19:30
 */
@Service
public class UserauthServiceImpl  implements UserauthService{
	@Autowired
	private AmUserClient userauthClient;
	@Autowired
	private AmTradeClient amTradeClient;
	@Override
	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest) {
		
		return userauthClient.userauthlist(adminUserAuthListRequest);
	}

	@Override
	public AdminUserAuthListResponse cancelInvestAuth(int userId) {
		
		return userauthClient.cancelInvestAuth(userId);
	}

	@Override
	public AdminUserAuthListResponse cancelCreditAuth(int userId) {
		return userauthClient.cancelCreditAuth(userId);
	}

	@Override
	public HjhAccedeResponse canCancelAuth(Integer userId) {
		return amTradeClient.canCancelAuth(userId);
	}

	@Override
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest) {
		return userauthClient.userauthLoglist(adminUserAuthListRequest);
	}


}
