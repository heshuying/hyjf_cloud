package com.hyjf.cs.trade.service.aems.asset;

import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @version AemsAssetSearchService, v0.1 2018/8/27 10:18
 * @Author: Zha Daojian
 */
public interface AemsAssetSearchService extends BaseTradeService {
    /**
     * 查询资产状态
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     * @param request
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     **/
    ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request);
}
