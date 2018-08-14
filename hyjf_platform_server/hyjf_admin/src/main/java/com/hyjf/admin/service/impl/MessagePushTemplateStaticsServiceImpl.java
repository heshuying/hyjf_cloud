/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.MessagePushTemplateStaticsService;
import com.hyjf.am.response.admin.MessagePushTemplateStaticsResponse;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;

/**
 * @author fq
 * @version MessagePushTemplateStaticsServiceImpl, v0.1 2018/8/14 14:28
 */
@Service
public class MessagePushTemplateStaticsServiceImpl implements MessagePushTemplateStaticsService {
	@Autowired
	private CsMessageClient csMessageClient;

	@Override
	public MessagePushTemplateStaticsResponse selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
		return csMessageClient.selectTemplateStatics(request);
	}
}
