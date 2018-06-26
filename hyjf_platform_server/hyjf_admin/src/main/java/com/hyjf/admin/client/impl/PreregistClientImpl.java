/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.PreregistClient;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Service
public class PreregistClientImpl implements  PreregistClient{
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/adminSystem/getuser" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}

	@Override
	public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/adminSystem/getuser" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}

	@Override
	public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/adminSystem/getuser" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}
	




}
