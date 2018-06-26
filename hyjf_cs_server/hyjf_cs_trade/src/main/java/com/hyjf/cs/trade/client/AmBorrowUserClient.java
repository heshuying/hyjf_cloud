package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowUserVO;

public interface AmBorrowUserClient {

    public BorrowUserVO getBorrowUser(String borrowNid);
}
