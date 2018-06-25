package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;

import java.util.List;

public interface AmBorrowCarinfoClient {

    List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid);
}
