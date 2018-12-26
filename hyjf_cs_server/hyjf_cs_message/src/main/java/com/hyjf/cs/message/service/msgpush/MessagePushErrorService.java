/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush;

import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorService, v0.1 2018/8/14 22:45
 */
public interface MessagePushErrorService {

    /**
     * 获取列表记录数
     *
     * @return
     */
    Integer getRecordCount(MessagePushErrorRequest request);

    /**
     * 获取列表
     *
     * @return
     */
    List<MessagePushMsgHistory> getRecordList(MessagePushErrorRequest request, int limitStart, int limitEnd);

    /**
     * 获取单个信息
     *
     * @return
     */
    MessagePushMsgHistory getRecord(String id);
}
