/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.PreregistService;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Service
public class PreregistServiceImpl implements  PreregistService {
	@Autowired
	private AmUserClient preregistClient;

	@Override
	public AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest) {
		return preregistClient.getRecordList(adminPreRegistListRequest);
	}

	@Override
	public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		return preregistClient.getPreRegist(adminPreRegistListRequest);
	}

	@Override
	public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		return preregistClient.savePreRegist(adminPreRegistListRequest);
	}
}
