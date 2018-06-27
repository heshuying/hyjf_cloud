/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;

import java.util.List;

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
}
