/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.MessagePushPlatStaticsService;
import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version MessagePushTagServiceImpl, v0.1 2018/8/14 15:43
 */
@Service
public class MessagePushPlatStaticsServiceImpl implements MessagePushPlatStaticsService {
	@Autowired
	private CsMessageClient csMessageClient;

	@Override
	public MessagePushPlatStaticsResponse selectTemplateStatics(MessagePushPlatStaticsRequest request) {
		return csMessageClient.selectPushPlatTemplateStatics(request);
	}
}
