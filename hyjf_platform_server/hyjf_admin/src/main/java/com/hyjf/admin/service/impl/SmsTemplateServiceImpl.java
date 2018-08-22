/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.SmsTemplateService;
import com.hyjf.am.response.config.SmsTemplateResponse;
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
	private AmConfigClient amConfigClient;

	@Override
	public List<SmsTemplateVO> findAll() {
		return amConfigClient.findSmsAll();
	}

	@Override
	public SmsTemplateResponse findSmsTemplate(SmsTemplateRequest request) {
		return amConfigClient.findSmsTemplate(request);
	}

	@Override
	public int insertSmsTemplate(SmsTemplateRequest request) {
		return amConfigClient.insertSmsTemplate(request);
	}

	@Override
	public int updateStatus(SmsTemplateRequest request) {
		return amConfigClient.updateStatus(request);
	}

	@Override
	public void closeAction(SmsTemplateRequest request) {
		amConfigClient.closeSmsTemplate(request);
	}

	@Override
	public int updateSmsTemplate(SmsTemplateRequest request) {
		return amConfigClient.updateSmsTemplate(request);
	}

	@Override
	public SmsTemplateVO selectSmsTemByTplCode(SmsTemplateRequest request) {
		if (request != null) {
			return amConfigClient.selectSmsTemByTplCode(request.getTplCode());
		}
        return null;
	}
}
