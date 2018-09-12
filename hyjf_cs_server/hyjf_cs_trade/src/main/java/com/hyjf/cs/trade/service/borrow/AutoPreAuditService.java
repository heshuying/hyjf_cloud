/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow;

import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.cs.trade.bean.MQBorrow;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author fuqiang
 * @version AutoPreAuditService, v0.1 2018/6/14 16:34
 */
public interface AutoPreAuditService extends BaseTradeService {
    /**
     * 发送自动关联计划消息
     * @param mqBorrow
     * @param hyjfBorrowIssueGroup
     */
    void sendToMQ(MQBorrow mqBorrow, String hyjfBorrowIssueGroup);

    /**
     * 资产自动备案-自动初审
     *
     * @param hjhPlanAssetVO
     * @param hjhAssetBorrowType
     * @return
     */
    boolean updateRecordBorrow(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowType);

    /**
     * 手动录标自动备案-自动初审
     *
     * @param borrow
     * @return
     */
    boolean updateRecordBorrow(BorrowVO borrow);
}
