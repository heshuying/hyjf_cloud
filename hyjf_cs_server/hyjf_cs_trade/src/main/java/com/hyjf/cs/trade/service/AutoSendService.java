/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.HjhPlanAssetVO;

/**
 * 自动录标
 *
 * @author fuqiang
 * @version AutoSendService, v0.1 2018/6/12 17:04
 */
public interface AutoSendService extends ApiAssetPushService {
    /**
     * 资产自动录标
     *
     * @param hjhPlanAsset
     * @param hjhAssetBorrowTypeVO
     * @return
     */
    boolean insertSendBorrow(HjhPlanAssetVO hjhPlanAsset, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO) throws Exception;
}
