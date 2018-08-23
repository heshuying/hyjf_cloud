package com.hyjf.cs.message.service.Impl;

import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgMongoDao;
import com.hyjf.cs.message.service.MessagePushNoticesService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushNoticesServiceImpl, v0.1 2018/8/14 17:34
 */
@Service
public class MessagePushNoticesServiceImpl implements MessagePushNoticesService {
    @Autowired
    MessagePushMsgMongoDao messagePushMsgMongoDao;

    @Override
    public List<MessagePushMsg> getRecordList(MessagePushNoticesRequest request, Integer offset,Integer limit) {
        return messagePushMsgMongoDao.getRecordList(request,offset,limit);
    }

    @Override
    public Integer getRecordCount(MessagePushNoticesRequest request) {
        return messagePushMsgMongoDao.countRecordList(request);
    }

    @Override
    public void insertRecord(MessagePushNoticesRequest request) {
        messagePushMsgMongoDao.insertRecord(request);
    }

    @Override
    public void deleteRecord(String id) {
        messagePushMsgMongoDao.deleteRecord(id);
    }

    @Override
    public void updateRecord(MessagePushMsg record) {
        messagePushMsgMongoDao.updateRecord(record);
    }

    @Override
    public MessagePushMsg getRecord(MessagePushNoticesRequest request) {
        return messagePushMsgMongoDao.getRecord(request);
    }
}
