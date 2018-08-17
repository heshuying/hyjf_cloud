package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;

/**
 * @author lisheng
 * @version MessagePushHistoryService, v0.1 2018/8/14 19:59
 */

public interface MessagePushHistoryService {
    /**
     * 获取发送历史列表
     * @param request
     * @return
     */
    MessagePushHistoryResponse  getRecordList(MessagePushHistoryRequest request);

    /**
     * 获取消息推送标签列表
     * @return
     */
    MessagePushTagResponse getAllPushTagList();

}
