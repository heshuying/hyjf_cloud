/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;

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
}
