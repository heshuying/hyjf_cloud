/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;

/**
 * @author fuqiang
 * @version AutoRecordService, v0.1 2018/6/14 10:17
 */
public interface AutoRecordService extends BaseTradeService{

    /**
     * 备案
     * @param hjhPlanAssetVO
     * @param hjhAssetBorrowTypeVO
     * @return
     */
    boolean updateRecordBorrow(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO);

    /**
     * 发送自动初审消息
     * @param hjhPlanAssetVO
     * @param borrowPreauditGroup
     */
    void sendToMQ(HjhPlanAssetVO hjhPlanAssetVO, String borrowPreauditGroup);
}
