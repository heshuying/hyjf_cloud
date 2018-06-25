package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;

public interface BorrowManinfoService {

    BorrowManinfo getBorrowManinfoByNid(String borrowNid);
}
