/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.message.SmsLogRequest;

/**
 * @author fuqiang
 * @version SmsLogClient, v0.1 2018/6/23 15:24
 */
public interface SmsLogClient {
    /**
     * 查询消息中心短信发送记录
     *
     * @return
     */
    JSONObject smsLogList();

    /**
     * 根据条件查询消息中心短信发送记录
     *
     * @param request
     * @return
     */
    JSONObject findSmsLog(SmsLogRequest request);
}
