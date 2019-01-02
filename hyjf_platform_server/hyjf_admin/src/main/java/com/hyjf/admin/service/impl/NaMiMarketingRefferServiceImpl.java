package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.NaMiMarketingRefferService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/11/8.
 */
@Service
public class NaMiMarketingRefferServiceImpl  implements NaMiMarketingRefferService {

    @Autowired
    AmAdminClient amAdminClient;


    /**
     * 查询邀请人返现明细 列表
     * @param naMiMarketingRequest
     * @return
     */
    @Override
    public NaMiMarketingResponse selectNaMiMarketingRefferList(NaMiMarketingRequest naMiMarketingRequest){
        return amAdminClient.selectNaMiMarketingRefferList(naMiMarketingRequest);
    }
    @Override
    public IntegerResponse selectNaMiMarketingRefferCount(NaMiMarketingRequest request){
        return amAdminClient.selectNaMiMarketingRefferCount(request);

    }
    @Override
    public IntegerResponse selectNaMiMarketingRefferTotalCount(NaMiMarketingRequest request){
        return amAdminClient.selectNaMiMarketingRefferTotalCount(request);

    }
    /**
     * 查询邀请人返现统计 列表
     * @param naMiMarketingRequest
     * @return
     */
    @Override
    public NaMiMarketingResponse selectNaMiMarketingRefferTotalList(NaMiMarketingRequest naMiMarketingRequest){
        return amAdminClient.selectNaMiMarketingRefferTotalList(naMiMarketingRequest);
    }
}
