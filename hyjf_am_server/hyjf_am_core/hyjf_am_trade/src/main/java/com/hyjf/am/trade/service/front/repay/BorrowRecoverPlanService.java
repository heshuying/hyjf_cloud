package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;

import java.util.List;

/**
 * @author hesy
 * @version BorrowRecoverPlanService, v0.1 2018/7/4 10:41
 */
public interface BorrowRecoverPlanService {
    BorrowRecoverPlan selectRecoverPlanById(Integer id);

    List<BorrowRecoverPlan> selectRecoverPlan(String borrowNid, Integer period);

    List<BorrowRecoverPlan> selectRecoverPlanListByTenderNid(String tenderNid);

    /**
     * 获取用户投资订单还款详情
     *
     * @param nid
     * @return
     */
    List<BorrowRecoverPlan> selectBorrowRecoverPlanListByNid(String nid);
}
