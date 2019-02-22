/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.mongo.mc.MessagePushMessageDao;
import com.hyjf.cs.message.service.msgpush.MessagePushMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public MessagePushMsg getMessagePushMsgById(String id) {
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
    public Integer deleteMessagePushMsg(List<MessagePushMsg> recordList) {
        messagePushMessageDao.deleteBatch(recordList);
        return 1;
    }

    @Override
    public long countMessagePushMsg(MessagePushMsgRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (request.getStartDateSrch() != null || request.getEndDateSrch() != null) {
            int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartDateSrch() + " 00:00:00");
            int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndDateSrch() + " 23:59:59");
            criteria.and("sendTime").gte(startTime).lte(endTime);
        }
        if (request.getTagId() != null) {
            criteria.and("tagId").is(request.getTagId());
        }
        if (StringUtils.isNotBlank(request.getMsgTitle())) {
            criteria.and("msgTitle").is(request.getMsgTitle());
        }
        if (StringUtils.isNotBlank(request.getMsgCode())) {
            criteria.and("msgCode").is(request.getMsgCode());
        }
        if (StringUtils.isNotBlank(request.getLastupdateUserName())) {
            criteria.and("lastupdateUserName").is(request.getLastupdateUserName());
        }
        if (request.getMsgTerminal() != null) {
            String msgTerminal = request.getMsgTerminal();
            criteria.and("msgTerminal").regex(msgTerminal);
        }
        if (request.getMsgSendStatus() != null) {
            criteria.and("msgSendStatus").is(request.getMsgSendStatus());
        }
        query.addCriteria(criteria);
        return messagePushMessageDao.count(query);
    }
}
