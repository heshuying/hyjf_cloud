/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.service.message.MessagePushTemplateStaticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
	private AmConfigClient amConfigClient;

	@Override
	public List<MessagePushTemplateStatics> selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
		return staticsDao.selectTemplateStatics(request);
	}

	@Override
	public int selectCount(MessagePushTemplateStaticsRequest request) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(request.getStartDateSrch()) && StringUtils.isNotBlank(request.getEndDateSrch())) {
			criteria.and("sendTime").gte(request.getStartDateSrch() + " 00:00:00")
					.lte(request.getEndDateSrch() + " 23:59:59");
		}
		if (StringUtils.isNotBlank(request.getMsgTitleSrch())) {
			criteria.and("msgTitle").is(request.getMsgTitleSrch());
		}
		if (StringUtils.isNotBlank(request.getMsgCodeSrch())) {
			criteria.and("msgCode").is(request.getMsgCodeSrch());
		}
		if (StringUtils.isNotBlank(request.getTagIdSrch())) {
			criteria.and("tagId").is(request.getTagIdSrch());
		}
		query.addCriteria(criteria);
		return staticsDao.count(query).intValue();
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
