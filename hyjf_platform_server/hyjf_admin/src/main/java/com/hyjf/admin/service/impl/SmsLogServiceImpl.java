/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogServiceImpl, v0.1 2018/6/23 15:24
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public SmsLogResponse smsLogList() {
        return csMessageClient.smsLogList();
    }

    @Override
    public SmsLogResponse findSmsLog(SmsLogRequest request) {
        return csMessageClient.findSmsLog(request);
    }

    @Override
    public SmsOntimeResponse queryTime(SmsLogRequest request) {
        return csMessageClient.queryTime(request);
    }

    @Override
    public List<SmsLogVO> findSmsLogList(SmsLogRequest request) {
        SmsLogResponse response = csMessageClient.findSmsLog(request);
        if(response != null & response.getResultList() != null ){
            return  response.getResultList();
        }
        return new ArrayList<>();
    }
}
