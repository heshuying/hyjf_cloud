package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;

public interface BorrowTenderService {

    Integer getCountBorrowTenderService(Integer userId, String borrowNid);

    BorrowTender selectBorrowTender(BorrowTenderRequest request);
}
