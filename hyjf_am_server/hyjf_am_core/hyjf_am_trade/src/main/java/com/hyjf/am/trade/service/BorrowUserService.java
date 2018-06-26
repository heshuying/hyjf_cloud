package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowUsers;

public interface BorrowUserService {

    BorrowUsers getBorrowUserByNid(String borrowNid);
}
