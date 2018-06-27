package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;

public interface AmBorrowCreditClient {

    BorrowCreditDetailVO getCreditDetail(String creditNid);
}
