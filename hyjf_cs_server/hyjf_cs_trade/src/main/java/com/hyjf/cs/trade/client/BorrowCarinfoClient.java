package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;

import java.util.List;

public interface BorrowCarinfoClient {

    List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid);
}
