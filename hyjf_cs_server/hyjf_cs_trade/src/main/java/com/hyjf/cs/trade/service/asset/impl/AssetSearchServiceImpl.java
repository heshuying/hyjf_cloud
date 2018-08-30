package com.hyjf.cs.trade.service.asset.impl;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.asset.AssetSearchService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version AssetSearchServiceImpl, v0.1 2018/8/27 10:23
 * @Author: Zha Daojian
 */
@Service
public class AssetSearchServiceImpl extends BaseTradeServiceImpl implements AssetSearchService {

    @Autowired
    public AmTradeClient amTradeClient;

    /**
     * 查询资产状态
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     * @param assetListRequest
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     **/
    @Override
    public AssetDetailCustomizeVO selectStatusById(AssetListRequest assetListRequest) {
        AssetDetailCustomizeVO assetDetailCustomizeVO = amTradeClient.findDetailById(assetListRequest);
        return assetDetailCustomizeVO;
    }

}
