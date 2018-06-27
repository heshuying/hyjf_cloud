package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

import java.util.List;

/**
 * @author xiasq
 * @version BorrowApicronClient, v0.1 2018/6/19 14:26
 */
public interface BorrowApicronClient {
    /**
     *
     * @param extraYieldRepayStatus 融通宝加息利息还款状态0未完成1已完成
     * @param apiType 0放款1还款
     * @return
     */
    List<BorrowApicronVO> getBorrowApicronList(int extraYieldRepayStatus, int apiType);

    /**
     * 计划退出查询判断标的是否还款
     * @param borrowNid
     * @return
     */
    List<BorrowApicronVO> selectBorrowApicronListByBorrowNid(String borrowNid);
}
