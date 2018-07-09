package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * @author xiasq
 * @version BorrowClient, v0.1 2018/6/19 15:33
 */
public interface BorrowClient {
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 根据用户id获取用户总投资笔数
     * @author zhangyk
     * @date 2018/7/5 17:15
     */
    int getTotalInvestCountByUserId(Integer userId);
}
