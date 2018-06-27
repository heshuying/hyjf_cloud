package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;

public interface BorrowManinfoClient {

	/**
	 * 借款信息
	 * @param borrowNid
	 * @return
	 */
    BorrowManinfoVO getBorrowManinfo(String borrowNid);
}
