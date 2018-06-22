/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.client.messagePushTemplateClient;

import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateClientImpl, v0.1 2018/6/21 18:33
 */
@Service
public class MessagePushTemplateClientImpl implements MessagePushTemplateClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<MessagePushTemplateVO> getAllTemplates() {
		MessagePushTemplateResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/messagePushTemplate/getAllTemplates", MessagePushTemplateResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
