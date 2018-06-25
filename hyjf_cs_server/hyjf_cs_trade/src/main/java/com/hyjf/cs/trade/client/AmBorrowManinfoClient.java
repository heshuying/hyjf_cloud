package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;

public interface AmBorrowManinfoClient {


    BorrowManinfoVO getBorrowManinfo(String borrowNid);
}
