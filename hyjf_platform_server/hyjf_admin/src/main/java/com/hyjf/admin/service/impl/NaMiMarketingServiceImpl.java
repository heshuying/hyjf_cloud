package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.NaMiMarketingService;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version NaMiMarketingServiceImpl, v0.1 2018/12/26 13:48
 */
@Service
public class NaMiMarketingServiceImpl  implements NaMiMarketingService {

    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public NaMiMarketingResponse getRecordList(NaMiMarketingRequest naMiMarketingRequest) {
        return amAdminClient.getNaMiMarketingList(naMiMarketingRequest);
    }

    @Override
    public NaMiMarketingResponse getPerformanceList(NaMiMarketingRequest naMiMarketingRequest) {
        return amAdminClient.getPerformanceList(naMiMarketingRequest);
    }

    @Override
    public NaMiMarketingResponse getPerformanceInfo(NaMiMarketingRequest naMiMarketingRequest) {
        return amAdminClient.getPerformancInfo(naMiMarketingRequest);
    }
}
