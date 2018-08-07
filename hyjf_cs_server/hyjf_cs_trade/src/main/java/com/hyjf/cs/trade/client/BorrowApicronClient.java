package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

import java.util.List;

/**
 * @author xiasq
 * @version BorrowApicronClient, v0.1 2018/6/19 14:26
 */
public interface BorrowApicronClient {
    /**
     * 计划退出查询判断标的是否还款
     * @param borrowNid
     * @return
     */
    List<BorrowApicronVO> selectBorrowApicronListByBorrowNid(String borrowNid);
}
