package com.hyjf.cs.message.service.impl;

import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.cs.message.service.MessagePushHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushHistoryServiceImpl, v0.1 2018/8/14 20:13
 */
@Service
public class MessagePushHistoryServiceImpl implements MessagePushHistoryService {

    @Autowired
    MessagePushMsgHistoryDao messagePushMsgHistoryDao;

    /**
     * 获取历史消息列表
     * @param request
     * @return
     */
    @Override
    public List<MessagePushMsgHistory> getRecordList(MessagePushHistoryRequest request,Integer offset,Integer limit) {
        return messagePushMsgHistoryDao.getRecordList(request,offset,limit);
    }

    /**
     * 获取历史消息条数
     * @param request
     * @return
     */
    @Override
    public Integer getRecordCount(MessagePushHistoryRequest request){
        return messagePushMsgHistoryDao.countRecordList(request);
    }
}
