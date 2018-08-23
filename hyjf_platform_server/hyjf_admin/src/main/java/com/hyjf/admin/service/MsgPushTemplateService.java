/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;

/**
 * @author fuqiang
 * @version MsgPushTemplateService, v0.1 2018/6/26 9:36
 */
public interface MsgPushTemplateService {
    /**
     * 查询所有消息推送模板
     *
     * @return
     */
    MessagePushTemplateResponse findAll();

    /**
     * 根据条件查询消息推送模板
     *
     * @param request
     * @return
     */
    MessagePushTemplateResponse findMsgPushTemplate(MsgPushTemplateRequest request);

    /**
     * 新增消息推送模板
     *
     * @param request
     */
    void insertMsgPushTemplate(MsgPushTemplateRequest request);
}
