package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

public interface BorrowTenderClient {

    public Integer  countUserInvest(Integer userId, String borrowNid);

	public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest);
    
    
}
