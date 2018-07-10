package com.hyjf.am.trade.service.repay;

import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;

/**
 * @author hesy
 * @version BankRepayFreezeLogService, v0.1 2018/7/9 16:02
 */
public interface BankRepayFreezeLogService {
    Integer insertRepayFreezeLog(BankRepayFreezeLogRequest requestBean);

    Integer deleteFreezeLogsByOrderId(String orderId);
}
