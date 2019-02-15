package com.hyjf.cs.trade.service.aems.asset.impl;

import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.aems.asset.AemsAssetSearchService;
import com.hyjf.cs.trade.service.asset.AssetSearchService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/5 13:40
 * @param 
 * @return 
 **/
@Service
public class AemsAssetSearchServiceImpl extends BaseTradeServiceImpl implements AemsAssetSearchService {

    @Autowired
    public AmTradeClient amTradeClient;

    /**
     * Aems查询资产状态
     * @author Zha Daojian
     * @date 2018/12/5 13:40
     * @param request
     * @return com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO
     **/
    @Override
    public ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request) {
        ApiAssetStatusCustomizeVO apiAssetStatusCustomizeVO = amTradeClient.selectAssetStatusById(request);
        return apiAssetStatusCustomizeVO;
    }

}
