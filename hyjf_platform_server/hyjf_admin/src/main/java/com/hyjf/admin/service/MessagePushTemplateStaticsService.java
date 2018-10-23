/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MessagePushTemplateStaticsResponse;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;

/**
 * @author fq
 * @version MessagePushTemplateStaticsService, v0.1 2018/8/14 14:28
 */
public interface MessagePushTemplateStaticsService {
    /**
     * app消息推送-模板消息统计报表
     * @return
     */
    MessagePushTemplateStaticsResponse selectTemplateStatics(MessagePushTemplateStaticsRequest request);

}
