package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowUser;

public interface BorrowUserService {

    BorrowUser getBorrowUserByNid(String borrowNid);
}
