package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;

import java.util.List;

/**
 * @author hesy
 * @version BorrowRecoverPlanClient, v0.1 2018/7/4 10:51
 */
public interface BorrowRecoverPlanClient {
    BorrowRecoverPlanVO selectRecoverPlanById(Integer id);

    List<BorrowRecoverPlanVO> selectRecoverPlan(String borrowNid, Integer period);
}
