/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.mongo.mc.MessagePushPlatStaticsDao;
import com.hyjf.cs.message.service.message.MessagePushPlatStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
