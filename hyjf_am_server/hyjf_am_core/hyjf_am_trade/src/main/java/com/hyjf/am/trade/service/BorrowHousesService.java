package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowHouses;

import java.util.List;

public interface BorrowHousesService {

    List<BorrowHouses> getBorrowHousesByNid(String borrowNid);
}
