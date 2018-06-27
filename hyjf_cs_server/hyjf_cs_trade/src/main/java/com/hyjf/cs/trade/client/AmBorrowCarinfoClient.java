package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.WebCarinfoVO;

import java.util.List;

public interface AmBorrowCarinfoClient {

    List<WebCarinfoVO> getBorrwCarinfo(String borrowNid);
}
