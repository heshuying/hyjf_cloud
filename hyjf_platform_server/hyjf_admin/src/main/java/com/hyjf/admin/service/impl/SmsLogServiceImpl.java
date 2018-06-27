/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.SmsLogClient;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.resquest.message.SmsLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version SmsLogServiceImpl, v0.1 2018/6/23 15:24
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogClient smsLogClient;

    @Override
    public JSONObject smsLogList() {
        return smsLogClient.smsLogList();
    }

    @Override
    public JSONObject findSmsLog(SmsLogRequest request) {
        return smsLogClient.findSmsLog(request);
    }
}
