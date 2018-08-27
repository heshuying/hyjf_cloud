/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.MessagePushMsgService;
import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushMsgServiceImpl, v0.1 2018/8/15 16:03
 */
@Service
public class MessagePushMsgServiceImpl implements MessagePushMsgService {

    @Autowired
    private CsMessageClient csMessageClient;
    @Override
    public MessagePushMsgResponse selectMessagePushMsg(MessagePushMsgRequest request) {
        return csMessageClient.selectMessagePushMsg(request);
    }

    @Override
    public MessagePushMsgResponse getRecord(String id) {
        return csMessageClient.getMessagePushMsgById(id);
    }

    @Override
    public MessagePushMsgResponse insertRecord(MessagePushMsgVO templateVO) {
        return csMessageClient.insertMessagePushMsg(templateVO);
    }

    @Override
    public MessagePushMsgResponse updateMessagePushMsg(MessagePushMsgRequest templateRequest) {
        return csMessageClient.updateMessagePushMsg(templateRequest);
    }

    @Override
    public MessagePushMsgResponse deleteAction(MessagePushMsgRequest request) {
        return csMessageClient.deleteMessagePushMsg(request);
    }
}
