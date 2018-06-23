/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AmBorrowRepayPlanClient, v0.1 2018/6/22 18:44
 */
public interface AmBorrowRepayPlanClient {
    /**
     * 根据标的编号查询还款计划
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder);

    /**
     * 短信发送后更新borrowRecoverPlan
     *
     * @param borrowRepayPlanVO
     * @return
     */
    Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO);
}
