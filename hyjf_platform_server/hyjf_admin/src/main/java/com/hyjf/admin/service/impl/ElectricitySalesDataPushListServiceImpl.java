package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.ElectricitySalesDataPushListService;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
@Service
public class ElectricitySalesDataPushListServiceImpl extends BaseServiceImpl implements ElectricitySalesDataPushListService{
	
    @Autowired
    private AmUserClient amUserClient;

	@Override
	public ElectricitySalesDataPushListResponse searchList(ElectricitySalesDataPushListRequest request) {
		return amUserClient.searchElectricitySalesDataPushList(request);
	}

	@Override
	public ElectricitySalesDataPushListResponse insertElectricitySalesDataPushList(
			ElectricitySalesDataPushListRequest request) {
		return amUserClient.insertElectricitySalesDataPushList(request);
	}

}
