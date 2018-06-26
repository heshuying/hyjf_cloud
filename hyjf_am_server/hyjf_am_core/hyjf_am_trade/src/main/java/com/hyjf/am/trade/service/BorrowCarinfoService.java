package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowCarinfo;

import java.util.List;

public interface BorrowCarinfoService {

    List<BorrowCarinfo> getBorrowCarinfoListByNid(String borrowNid);
}
