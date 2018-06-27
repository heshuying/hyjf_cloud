/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;

import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushTemplateClient, v0.1 2018/6/26 9:39
 */
public interface MsgPushTemplateClient {
    /**
     * 查询所有消息推送模板
     *
     * @return
     */
    List<MessagePushTemplateVO> findAll();

    /**
     * 根据条件查询消息推送模板
     *
     * @param request
     * @return
     */
    List<MessagePushTemplateVO> findMsgPushTemplate(MsgPushTemplateRequest request);

    /**
     * 新增消息推送模板
     *
     * @param request
     */
    void insertMsgPushTemplate(MsgPushTemplateRequest request);

    /**
     * 根据tagName获取tagCode
     *
     * @param tagName
     * @return
     */
    MessagePushTagVO findMsgTagByTagName(String tagName);
}
