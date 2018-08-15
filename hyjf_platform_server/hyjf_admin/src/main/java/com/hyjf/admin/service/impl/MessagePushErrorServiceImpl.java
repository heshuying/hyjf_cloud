/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangzw
 * @version MessagePushErrorServiceImpl, v0.1 2018/8/14 22:09
 */
@Service
public class MessagePushErrorServiceImpl implements MessagePushErrorService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public MessagePushErrorResponse getListByConditions(MessagePushErrorRequest request) {
        return csMessageClient.getListByConditions(request);
    }

    @Override
    public MessagePushErrorResponse update(MessagePushErrorRequest request) {
        return csMessageClient.update(request);
    }
}
