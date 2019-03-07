/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.mongo.mc.MessagePushPlatStaticsDao;
import com.hyjf.cs.message.service.message.MessagePushPlatStaticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fq
 * @version MessagePushPlatStaticsServiceImpl, v0.1 2018/8/14 16:24
 */
@Service
public class MessagePushPlatStaticsServiceImpl implements MessagePushPlatStaticsService {
    @Autowired
    private MessagePushPlatStaticsDao staticsDao;

    @Override
    public List<MessagePushPlatStatics> selectPlatStatics(MessagePushPlatStaticsRequest request) {
        return staticsDao.selectPlatStatics(request);
    }

    @Override
    public int selectCount(MessagePushPlatStaticsRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getStartDateSrch()) && StringUtils.isNotBlank(request.getEndDateSrch())) {
            criteria.and("staDate").gte(request.getStartDateSrch())
                    .lte(request.getEndDateSrch());
        }
        if (StringUtils.isNotBlank(request.getTagIdSrch())) {
            criteria.and("tagId").is(request.getTagIdSrch());
        }
        query.addCriteria(criteria);
        return staticsDao.count(query).intValue();
    }
}
