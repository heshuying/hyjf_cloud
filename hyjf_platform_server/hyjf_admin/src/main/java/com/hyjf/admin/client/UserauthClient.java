/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;


import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;

/**
 * @author DongZeShan
 * @version UserauthService.java, v0.1 2018年6月27日 下午2:19:30
 */
public interface UserauthClient {
	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest);
	public AdminUserAuthListResponse cancelInvestAuth(int userId, String ordId);
	public AdminUserAuthListResponse cancelCreditAuth( int userId,  String ordId);
	 //@GetMapping("/canCancelAuth/{userId}")
	public HjhAccedeResponse canCancelAuth(Integer userId);
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest);

}
