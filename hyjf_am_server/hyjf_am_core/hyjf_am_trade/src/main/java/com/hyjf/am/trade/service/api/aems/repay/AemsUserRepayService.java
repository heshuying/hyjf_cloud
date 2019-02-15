package com.hyjf.am.trade.service.api.aems.repay;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;

public interface AemsUserRepayService extends BaseService {
    /**
     * 根据还款人id，项目编号查询相应的项目
     * @param userId
     * @param roleId
     * @param borrowNid
     * @return
     */
    BorrowInfoVO getBorrow(int userId, String roleId, String borrowNid);
}
