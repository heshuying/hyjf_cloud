/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.SmsTemplateClient;
import com.hyjf.am.response.config.SmsTemplateResponse;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateClientImpl, v0.1 2018/6/25 10:16
 */
@Service
public class SmsTemplateClientImpl implements SmsTemplateClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<SmsTemplateVO> findAll() {
		SmsTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/message/smsTemplate/findAll", SmsTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<SmsTemplateVO> findSmsTemplate(SmsTemplateRequest request) {
		SmsTemplateResponse response = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/message/smsTemplate/findSmsTemplate", request,
						SmsTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public void insertSmsTemplate(SmsTemplateRequest request) {
		restTemplate.postForEntity("http://AM-CONFIG/am-config/message/smsTemplate/insertTemplate", request, Object.class);
	}
}
