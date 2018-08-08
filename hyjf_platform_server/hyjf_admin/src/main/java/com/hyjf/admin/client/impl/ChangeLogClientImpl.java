package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.ChangeLogClient;
import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.resquest.user.ChangeLogRequest;
@Service
public class ChangeLogClientImpl  implements ChangeLogClient{

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ChangeLogResponse getChangeLogList(ChangeLogRequest clr) {
		ChangeLogResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/changelog/init",clr, ChangeLogResponse.class)
                .getBody();

        return response;
	}

}
