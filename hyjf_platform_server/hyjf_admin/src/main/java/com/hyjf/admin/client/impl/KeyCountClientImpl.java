package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.KeyCountClient;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyCountClientImpl implements KeyCountClient {

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public KeyCountResponse searchAction(KeyCountRequest request) {
		KeyCountResponse response = restTemplate.postForObject("http://AM-USER/am-user/extensioncenter/keycount/searchaction",
				request, KeyCountResponse.class);
		return  response;
		
	}

}
