package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * @author xiasq
 * @version BorrowClient, v0.1 2018/6/19 15:33
 */
public interface BorrowClient {
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 更新标的信息
     * @param borrowRegistRequest
     * @return
     */
    int updateBorrowRegist(BorrowRegistRequest borrowRegistRequest);

    /**
     * 更新标的信息(受托支付备案)
     * @param borrowRegistRequest
     * @return
     */
    int updateEntrustedBorrowRegist(BorrowRegistRequest borrowRegistRequest);
}
