package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

/**
 * @author xiasq
 * @version BorrowClient, v0.1 2018/6/19 15:33
 */
public interface BorrowClient {
    BorrowAndInfoVO selectBorrowByNid(String borrowNid);

}
