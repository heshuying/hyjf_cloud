/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.MsgPushTemplateClient;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushTemplateClientImpl, v0.1 2018/6/26 9:39
 */
@Service
public class MsgPushTemplateClientImpl implements MsgPushTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

	@Override
	public List<MessagePushTemplateVO> findAll() {
		MessagePushTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/messagePushTemplate/getAllTemplates",
						MessagePushTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<MessagePushTemplateVO> findMsgPushTemplate(MsgPushTemplateRequest request) {
		MessagePushTemplateResponse response = restTemplate
				.postForEntity("http://AM-CONFIG/am-config/messagePushTemplate/findMsgPushTemplate", request,
						MessagePushTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
		restTemplate.postForEntity("http://AM-CONFIG/am-config/messagePushTemplate/insertMsgPushTemplate", request,
				Object.class);
	}

	@Override
	public MessagePushTagVO findMsgTagByTagName(String tagName) {
		MessagePushTagVO result = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/messagePushTemplate/findMsgTagByTagName/" + tagName,
						MessagePushTagVO.class)
				.getBody();
		return result;
	}
}

