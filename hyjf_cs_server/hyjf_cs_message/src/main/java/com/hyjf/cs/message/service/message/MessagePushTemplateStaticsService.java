/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message;

import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;

import java.util.List;

/**
 * @author fq
 * @version MessagePushTemplateStaticsService, v0.1 2018/8/14 14:54
 */
public interface MessagePushTemplateStaticsService {
    /**
     * 查询模板消息统计报表
     * @param request
     * @return
     */
    List<MessagePushTemplateStatics> selectTemplateStatics(MessagePushTemplateStaticsRequest request);

    /**
     * 查詢數量
     * @param request
     * @return
     */
    int selectCount(MessagePushTemplateStaticsRequest request);

    /**
     * 获取标签
     * @param tagId
     * @return
     */
    String selectTagName(String tagId);
}
