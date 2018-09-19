package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUtmReadPermissionsServiceImpl implements AdminUtmReadPermissionsService {
	@Autowired
	private AmConfigClient amConfigClient;
	@Override
	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request){
		return  amConfigClient.searchAction(request);
	}

	@Override
	public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean) {
		return amConfigClient.updateAction(requestBean);
	}


	@Override
	public AdminUtmReadPermissionsResponse deleteById(Integer id) {
		return amConfigClient.deleteById(id);
	}
}
