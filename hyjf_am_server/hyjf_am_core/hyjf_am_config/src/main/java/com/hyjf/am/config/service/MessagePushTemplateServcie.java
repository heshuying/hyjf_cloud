/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateServcie, v0.1 2018/5/8 10:39
 */
public interface MessagePushTemplateServcie {
    /**
     * 根据tplCode查询消息推送模板
     *
     * @param tplCode
     * @return
     */
    MessagePushTemplate findMessagePushTemplateByCode(String tplCode);

    /**
     * 查询所有模版
     * @return
     */
    List<MessagePushTemplate> getAllTemplates();

    /**
     * 根据条件查询消息推送模板
     *
     * @param request
     * @return
     */
    List<MessagePushTemplate> findMsgPushTemplate(MsgPushTemplateRequest request);

    /**
     * 插入消息推送模板
     *
     * @param request
     */
    void insertMsgPushTemplate(MsgPushTemplateRequest request);
}
