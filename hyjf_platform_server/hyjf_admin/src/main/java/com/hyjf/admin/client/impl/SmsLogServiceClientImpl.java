/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.SmsLogClient;
import com.hyjf.am.resquest.message.SmsLogRequest;

/**
 * @author fuqiang
 * @version SmsLogServiceImpl, v0.1 2018/6/23 15:24
 */
@Service
public class SmsLogServiceClientImpl implements SmsLogClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public JSONObject smsLogList() {
		return restTemplate.getForEntity("http://CS-MESSAGE/cs-message/sms_log/list", JSONObject.class).getBody();
	}

	@Override
	public JSONObject findSmsLog(SmsLogRequest request) {
		return restTemplate.postForEntity("http://CS-MESSAGE/cs-message/sms_log/find", request, JSONObject.class)
				.getBody();
	}
}
