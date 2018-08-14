/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message;

import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;

import java.util.List;

/**
 * @author fq
 * @version MessagePushPlatStaticsService, v0.1 2018/8/14 16:24
 */
public interface MessagePushPlatStaticsService {
    /**
     * 根据条件查询平台消息统计报表
     * @param request
     * @return
     */
    List<MessagePushPlatStatics> selectPlatStatics(MessagePushPlatStaticsRequest request);
}
