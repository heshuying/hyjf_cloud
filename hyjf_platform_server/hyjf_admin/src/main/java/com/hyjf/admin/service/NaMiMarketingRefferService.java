package com.hyjf.admin.service;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiehuili on 2018/11/8.
 */
public interface NaMiMarketingRefferService {



    /**
     * 查询邀请人返现明细 列表
     * @param naMiMarketingRequest
     * @return
     */
    NaMiMarketingResponse  selectNaMiMarketingRefferList(NaMiMarketingRequest naMiMarketingRequest);

    IntegerResponse selectNaMiMarketingRefferCount(NaMiMarketingRequest request);

    /**
     * 查询邀请人返现统计 列表
     * @param naMiMarketingRequest
     * @return
     */
    NaMiMarketingResponse selectNaMiMarketingRefferTotalList(NaMiMarketingRequest naMiMarketingRequest);

    IntegerResponse selectNaMiMarketingRefferTotalCount(NaMiMarketingRequest request);


}
