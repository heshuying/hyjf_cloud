package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AdminUtmReadPermissionsClient;
import com.hyjf.admin.client.ChannelStatisticsDetailClient;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdminUtmReadPermissionsClientImpl implements AdminUtmReadPermissionsClient {

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/searchaction",
				request, AdminUtmReadPermissionsResponse.class);
		return  response;
		
	}

	@Override
	public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/insert",
				requestBean, AdminUtmReadPermissionsResponse.class);
	}

	@Override
	public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/update",
				requestBean, AdminUtmReadPermissionsResponse.class);
	}


	@Override
	public AdminUtmReadPermissionsResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/delete/" + id,
				AdminUtmReadPermissionsResponse.class);
	}

	@Override
	public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
		AdminUtmReadPermissionsResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/getadminutmreadpermissions/" + userId,
				AdminUtmReadPermissionsResponse.class);
		if(response != null){
			return response.getResult();
		}
		return null;
	}

	@Override
	public AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form) {
//		return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/getreconciliationpage",form,
//				AppChannelReconciliationResponse.class);
		return null;
	}
}
