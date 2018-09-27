package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
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
	public UtmPlatResponse getUtmPlatList(){
		return  amConfigClient.getUtmPlatList();

	}
	@Override
	public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean) {
		return amConfigClient.insertAction(requestBean);
	}
	@Override
	public AdminUtmReadPermissionsResponse getRecord(AdminUtmReadPermissionsRequest requestBean){
		return amConfigClient.getAdminUtmReadPermissions(requestBean);

	}

	@Override
	public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean) {
		return amConfigClient.updateAction(requestBean);
	}


	@Override
	public AdminUtmReadPermissionsResponse deleteById(Integer id) {
		return amConfigClient.deleteById(id);
	}

	@Override
	public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(String userId) {
		return amConfigClient.selectAdminUtmReadPermissions(Integer.parseInt(userId));
	}
}
