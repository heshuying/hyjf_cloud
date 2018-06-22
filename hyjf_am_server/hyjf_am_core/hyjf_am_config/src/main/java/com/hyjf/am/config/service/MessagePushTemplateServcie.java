/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.vo.config.MessagePushTemplateVO;

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
}
