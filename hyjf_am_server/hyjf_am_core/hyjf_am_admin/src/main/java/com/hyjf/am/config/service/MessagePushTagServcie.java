/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.resquest.config.MessagePushTagRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTagServcie, v0.1 2018/6/26 11:47
 */
public interface MessagePushTagServcie {
    /**
     * 根据名称获取消息模板标签
     *
     * @param tagName
     * @return
     */
    MessagePushTag findMsgTagByTagName(String tagName);

    /**
     * 根据条件查询列表数
     * @param request
     * @return
     */
    Integer countRecord(MessagePushTagRequest request);

    /**
     * 根据条件查询列表
     * @param request
     * @param offset
     * @param offset1
     * @return
     */
    List<MessagePushTag> searchList(MessagePushTagRequest request, int offset, int offset1);

    /**
     * 根据id获取标签详情
     * @param id
     * @return
     */
    MessagePushTag getRecord(Integer id);

    /**
     * 添加标签
     * @param messagePushTag
     * @return
     */
    Integer insertAction(MessagePushTag messagePushTag);

    /**
     * 修改标签
     * @param messagePushTag
     * @return
     */
    Integer updateAction(MessagePushTag messagePushTag);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteAction(Integer id);


    /**
     * 检查标签编码是否唯一
     * @param id
     * @param tagCode
     * @return
     */
    int countByTagCode(Integer id, String tagCode);

    /**
     * 获取标签类型
     * @return
     */
    List<MessagePushTag> getAllPushTagList();

    /**
     * 获取标签类型
     * @return
     */
    List<MessagePushTag> getTagList();
}
