package com.hyjf.cs.message.service;

import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushHistoryService, v0.1 2018/8/14 20:13
 */

public interface MessagePushHistoryService {
    List<MessagePushMsgHistory> getRecordList(MessagePushHistoryRequest request, Integer offset,Integer limit);

    Integer getRecordCount(MessagePushHistoryRequest request);

    List<MessagePushTag> getPushTagList();

    List<MessagePushTag> getTagList();
}
