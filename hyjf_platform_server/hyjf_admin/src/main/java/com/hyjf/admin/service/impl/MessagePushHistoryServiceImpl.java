package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version MessagePushHistoryServiceImpl, v0.1 2018/8/14 19:59
 */
@Service
public class MessagePushHistoryServiceImpl implements MessagePushHistoryService {
    @Autowired
    AmMarketClient amMarketClient;
    /**
     * 获取发送历史列表
     * @param request
     * @return
     */
    @Override
    public MessagePushHistoryResponse getRecordList(MessagePushHistoryRequest request) {

        return amMarketClient.getRecordList(request);
    }
    /**
     * 获取消息推送标签列表
     * @return
     */
    @Override
    public MessagePushTagResponse getAllPushTagList() {

        return null;
    }
}
