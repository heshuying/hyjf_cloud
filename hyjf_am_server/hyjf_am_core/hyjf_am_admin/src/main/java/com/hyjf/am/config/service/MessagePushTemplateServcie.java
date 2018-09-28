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

    /**
     * 获取查询模板条数
     * @param request
     * @return
     */
    Integer countRecord(MsgPushTemplateRequest request);

    /**
     * 获取列表
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    List<MessagePushTemplate> searchList(MsgPushTemplateRequest request, int offset, int limit);

    /**
     * 根据id获取消息模板
     * @param id
     * @return
     */
    MessagePushTemplate findMsgPushTemplateById(Integer id);

    /**
     * 添加模板
     * @param messagePushTemplate
     * @return
     */
    Integer insertMessagePushTemplate(MessagePushTemplate messagePushTemplate);

    /**
     * 修改模板
     * @param messagePushTemplate
     * @return
     */
    Integer updateAction(MessagePushTemplate messagePushTemplate);

    /**
     * 删除模板
     * @param id
     * @return
     */
    Integer deleteAction(List<Integer> ids);

    /**
     * 根据条件查询模板
     * @param id
     * @param templateCode
     * @return
     */
    Integer countByTemplate(Integer id, String templateCode);
}
