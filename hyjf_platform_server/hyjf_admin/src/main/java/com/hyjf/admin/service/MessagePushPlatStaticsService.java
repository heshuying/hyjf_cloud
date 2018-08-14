/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;

/**
 * @author fq
 * @version MessagePushPlatStaticsService, v0.1 2018/8/14 15:43
 */
public interface MessagePushPlatStaticsService {
    /**
     * 查询平台消息统计报表
     * @param request
     * @return
     */
    MessagePushPlatStaticsResponse selectTemplateStatics(MessagePushPlatStaticsRequest request);
}
