package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;

public interface BorrowManinfoService {

    BorrowManinfo getBorrowManinfoByNid(String borrowNid);
}
