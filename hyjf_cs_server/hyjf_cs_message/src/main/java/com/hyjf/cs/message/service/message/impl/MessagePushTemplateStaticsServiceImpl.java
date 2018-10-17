/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.mongo.mc.MessagePushTagDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.service.message.MessagePushTemplateStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fq
 * @version MessagePushTemplateStaticsServiceImpl, v0.1 2018/8/14 14:54
 */
@Service
public class MessagePushTemplateStaticsServiceImpl implements MessagePushTemplateStaticsService {
	@Autowired
	private MessagePushTemplateStaticsDao staticsDao;
	@Autowired
	private MessagePushTagDao messagePushTagDao;
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public List<MessagePushTemplateStatics> selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
		return staticsDao.selectTemplateStatics(request);
	}

	@Override
	public int selectCount(MessagePushTemplateStaticsRequest request) {
		request.setCurrPage(0);
		List<MessagePushTemplateStatics> list = staticsDao.selectTemplateStatics(request);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return 0;
	}

	@Override
	public String selectTagName(String tagId) {
		MessagePushTagVO vo = amConfigClient.selectMsgTagByTagId(tagId);
		if (vo != null) {
			return vo.getTagName();
		}
		return null;
	}
}
