/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;

/**
 * @author dangzw
 * @version MessagePushErrorService, v0.1 2018/8/14 22:08
 */
public interface MessagePushErrorService {

    MessagePushErrorResponse getListByConditions(MessagePushErrorRequest request);

    MessagePushErrorResponse update(MessagePushErrorRequest request);
}
