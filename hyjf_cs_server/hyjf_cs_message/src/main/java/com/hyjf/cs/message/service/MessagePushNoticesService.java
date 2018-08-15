package com.hyjf.cs.message.service;

import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushNoticesService, v0.1 2018/8/14 17:34
 */

public interface MessagePushNoticesService {
    List<MessagePushMsg> getRecordList(MessagePushNoticesRequest request,Integer offset,Integer limit);

    Integer getRecordCount(MessagePushNoticesRequest request);

    void  insertRecord(MessagePushNoticesRequest request);

    void  deleteRecord(Integer id);

    void updateRecord(MessagePushMsg record);

    MessagePushMsg getRecord(MessagePushNoticesRequest request);
}
