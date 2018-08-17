/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.mongo.mc.MessagePushMessageDao;
import com.hyjf.cs.message.service.msgpush.MessagePushMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushMsgServiceImpl, v0.1 2018/8/15 16:28
 */
@Service
public class MessagePushMsgServiceImpl implements MessagePushMsgService {

    @Autowired
    private MessagePushMessageDao messagePushMessageDao;
    @Override
    public List<MessagePushMsg> selectMessagePushMsg(MessagePushMsgRequest request) {
        return messagePushMessageDao.selectMessagePushMsg(request);
    }

    @Override
    public MessagePushMsg getMessagePushMsgById(Integer id) {
        return messagePushMessageDao.getMessagePushMsgById(id);
    }

    @Override
    public Integer insertMessagePushMsg(MessagePushMsg msg) {
        messagePushMessageDao.insert(msg);
        return 1;
    }

    @Override
    public Integer updateMessagePushMsg(MessagePushMsg messagePushMsg) {
        messagePushMessageDao.save(messagePushMsg);
        return 1;
    }

    @Override
    public Integer deleteMessagePushMsg(List<Integer> recordList) {
        messagePushMessageDao.deleteBatch(recordList);
        return 1;
    }
}
