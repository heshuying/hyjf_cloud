package com.hyjf.cs.trade.service.asset;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @version AssetSearchService, v0.1 2018/8/27 10:18
 * @Author: Zha Daojian
 */
public interface AssetSearchService extends BaseTradeService {
    /**
     * 查询资产状态
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     * @param assetListRequest
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     **/
    AssetDetailCustomizeVO selectStatusById(AssetListRequest assetListRequest);
}
