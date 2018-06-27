/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.SmsTemplateClient;
import com.hyjf.admin.service.SmsTemplateService;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateServiceImpl, v0.1 2018/6/25 10:13
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

	@Autowired
	private SmsTemplateClient smsTemplateClient;

	@Override
	public List<SmsTemplateVO> findAll() {
		return smsTemplateClient.findAll();
	}

	@Override
	public List<SmsTemplateVO> findSmsTemplate(SmsTemplateRequest request) {
		return smsTemplateClient.findSmsTemplate(request);
	}

	@Override
	public void insertSmsTemplate(SmsTemplateRequest request) {
		smsTemplateClient.insertSmsTemplate(request);
	}
}
