/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush;

import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushMsgService, v0.1 2018/8/15 16:27
 */
public interface MessagePushMsgService {
    List<MessagePushMsg> selectMessagePushMsg(MessagePushMsgRequest request);

    MessagePushMsg getMessagePushMsgById(String id);

    Integer insertMessagePushMsg(MessagePushMsg msg);

    Integer updateMessagePushMsg(MessagePushMsg messagePushMsg);

    Integer deleteMessagePushMsg(List<MessagePushMsg> recordList);

}
