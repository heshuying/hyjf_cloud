/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;

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
}
