/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.CaiJingLogService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.CaiJingLogResponse;
import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version CaiJIngLogServiceImpl, v0.1 2019/6/10 9:44
 */
@Service
public class CaiJIngLogServiceImpl implements CaiJingLogService {

    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public CaiJingLogResponse queryCaiJingLog(CaiJingLogRequest request) {
        return csMessageClient.queryCaiJingLog(request);
    }

    @Override
    public BooleanResponse reQueryCaiJingLog(CaiJingLogRequest request) {
        return csMessageClient.reQueryCaiJingLog(request);
    }
}
