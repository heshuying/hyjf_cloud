/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.MsgPushTemplateClient;
import com.hyjf.admin.service.MsgPushTemplateService;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushTemplateServiceImpl, v0.1 2018/6/26 9:36
 */
@Service
public class MsgPushTemplateServiceImpl implements MsgPushTemplateService {

	@Autowired
	private MsgPushTemplateClient msgPushTemplateClient;

	@Override
	public List<MessagePushTemplateVO> findAll() {
		return msgPushTemplateClient.findAll();
	}

	@Override
	public List<MessagePushTemplateVO> findMsgPushTemplate(MsgPushTemplateRequest request) {
	    if (request != null) {
            String tagName = request.getTagName();
			MessagePushTagVO tag = msgPushTemplateClient.findMsgTagByTagName(tagName);
			if (tag != null && tag.getTagName() != null) {
				request.setTagCode(tag.getTagName());
			}
        }
		return msgPushTemplateClient.findMsgPushTemplate(request);
	}

	@Override
	public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
		msgPushTemplateClient.insertMsgPushTemplate(request);
	}
}
