package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;

/**
 * @author hesy
 * @version BankRepayFreezeLogClient, v0.1 2018/7/10 16:40
 */
public interface BankRepayFreezeLogClient {
    BankRepayFreezeLogVO getFreezeLogValid(Integer userId, String borrowNid);

    Integer deleteFreezeLogByOrderId(String orderId);

    Integer addFreezeLog(BankRepayFreezeLogRequest requestBean);
}
