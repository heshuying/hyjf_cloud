package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;

import java.util.List;

public interface BorrowHousesClient {

    List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid);

}
