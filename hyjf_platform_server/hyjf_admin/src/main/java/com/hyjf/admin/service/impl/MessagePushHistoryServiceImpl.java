package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushHistoryServiceImpl, v0.1 2018/8/14 19:59
 */
@Service
public class MessagePushHistoryServiceImpl implements MessagePushHistoryService {
    @Autowired
    AmMarketClient amMarketClient;
    @Autowired
    AmConfigClient amConfigClient;
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
    public List<MessagePushTagVO> getAllPushTagList() {
        return amConfigClient.getAllPushTagList();
    }


}
