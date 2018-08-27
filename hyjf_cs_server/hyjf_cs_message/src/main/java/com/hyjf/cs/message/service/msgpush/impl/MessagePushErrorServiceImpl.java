/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.jpush.*;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTagDao;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorServiceImpl, v0.1 2018/8/14 22:45
 */
@Service
public class MessagePushErrorServiceImpl implements MessagePushErrorService {

    @Autowired
    private MessagePushMsgHistoryDao messagePushMsgHistoryDao;
    @Autowired
    private MessagePushTagDao messagePushTagDao;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 获取列表记录数
     *
     * @return
     */
    @Override
    public Integer getRecordCount(MessagePushErrorRequest request) {
        return messagePushMsgHistoryDao.getRecordCount(request);
    }

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<MessagePushMsgHistory> getRecordList(MessagePushErrorRequest request, int limitStart, int limitEnd) {
        return messagePushMsgHistoryDao.getRecordList(request, limitStart, limitEnd);
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public List<MessagePushTag> getTagList() {
        return messagePushTagDao.getTagList();
    }

    /**
     * 获取单个信息
     *
     * @return
     */
    @Override
    public MessagePushMsgHistory getRecord(Integer id) {
        return messagePushMsgHistoryDao.getRecord(id);
    }
}
