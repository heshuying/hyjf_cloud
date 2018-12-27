package com.hyjf.admin.service;

import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;

/**
 * @author lisheng
 * @version NaMiMarketingService, v0.1 2018/12/26 13:47
 */

public interface NaMiMarketingService {

    /**
     * 查询邀请明细列表
     * @param naMiMarketingRequest
     * @return
     */
    NaMiMarketingResponse getRecordList(NaMiMarketingRequest naMiMarketingRequest);
}
