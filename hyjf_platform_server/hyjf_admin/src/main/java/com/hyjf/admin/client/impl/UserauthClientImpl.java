/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.UserauthClient;
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
public class UserauthClientImpl implements UserauthClient {

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * am-user层查询自动投资债转异常列表
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse response = restTemplate
	                .postForEntity("http://AM-USER/am-user/userauth/userauthlist",adminUserAuthListRequest, AdminUserAuthListResponse.class)
	                .getBody();

	        return response;
	}
	/**
	 * 同步用户授权状态
	 * @auth sunpeikai
	 * @param type 1自动投资授权  2债转授权
	 * @return
	 */
	@Override
	public JSONObject synUserAuth(Integer userId, Integer type) {
		JSONObject jsonObject = restTemplate
				.getForEntity("http://AM-USER/am-user/userauth/synuserauth/" + userId + "/" + type, JSONObject.class)
				.getBody();

		return jsonObject;
	}

	@Override
	public String getBankRetMsg(String retCode) {
		String retMsg = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/adminException/getBankRetMsg/" + retCode, String.class).getBody();
		return retMsg;
	}

	@Override
	public AdminUserAuthListResponse cancelInvestAuth(int userId, String ordId) {
		AdminUserAuthListResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userauth/userinvescancel/" + userId + ordId, AdminUserAuthListResponse.class).
                getBody();
		return response;
	}

	@Override
	public AdminUserAuthListResponse cancelCreditAuth(int userId, String ordId) {
		AdminUserAuthListResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userauth/usercreditcancel/" + userId + ordId, AdminUserAuthListResponse.class).
                getBody();
		return response;
	}

	@Override
	public HjhAccedeResponse canCancelAuth(Integer userId) {
		HjhAccedeResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhAccede/canCancelAuth/" + userId , HjhAccedeResponse.class).
                getBody();
		return response;
	}

	@Override
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest) {
		AdminUserAuthLogListResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userauth/userauthloglist",adminUserAuthListRequest, AdminUserAuthLogListResponse.class)
                .getBody();

        return response;
	}



}
