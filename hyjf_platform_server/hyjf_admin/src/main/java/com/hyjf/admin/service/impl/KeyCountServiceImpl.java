package com.hyjf.admin.service.impl;


import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.KeyCountService;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.user.KeyCountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyCountServiceImpl implements KeyCountService {
	@Autowired
	private AmUserClient amUserClient;
	@Override
	public KeyCountResponse searchAction(KeyCountRequest request){
		return amUserClient.searchAction(request);
	}

}
