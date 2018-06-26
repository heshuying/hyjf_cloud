/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.MailTemplateClient;
import com.hyjf.am.response.config.SmsMailTemplateResponse;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version MailTemplateServiceImpl, v0.1 2018/6/25 14:48
 */
@Service
public class MailTemplateServiceImpl implements MailTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

	@Override
	public List<SmsMailTemplateVO> findAll() {
		SmsMailTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findAll", SmsMailTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request) {
		SmsMailTemplateResponse response = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findMailTemplate", request,
						SmsMailTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public void insertMailTemplate(MailTemplateRequest request) {
		restTemplate.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/insertMailTemplate", request,
				Object.class);
	}
}
