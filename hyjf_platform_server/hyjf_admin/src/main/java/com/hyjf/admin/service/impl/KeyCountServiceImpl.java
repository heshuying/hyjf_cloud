package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AdminUtmReadPermissionsClient;
import com.hyjf.admin.client.KeyCountClient;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.admin.service.KeyCountService;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyCountServiceImpl implements KeyCountService {
	@Autowired
	private KeyCountClient keyCountClient;
	@Override
	public KeyCountResponse searchAction(KeyCountRequest request){
		return  keyCountClient.searchAction(request);
	}

}
