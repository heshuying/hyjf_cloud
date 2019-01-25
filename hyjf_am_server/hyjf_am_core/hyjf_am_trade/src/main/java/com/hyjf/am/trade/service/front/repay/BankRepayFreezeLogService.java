package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLog;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeLogService, v0.1 2018/7/9 16:02
 */
public interface BankRepayFreezeLogService {
    Integer insertRepayFreezeLog(BankRepayFreezeLogRequest requestBean);

    Integer deleteFreezeLogsByOrderId(String orderId);

    Integer deleteFreezeLogById(Integer id);

    BankRepayFreezeLog getFreezeLog(Integer userId, String borrowNid);

    BankRepayFreezeLog getBankFreezeLogByOrderId(String orderId);

    List<BankRepayFreezeLog> getFreezeLogValidAll(Integer limitStart, Integer limitEnd);

    Integer getFreezeLogValidAllCount();
}
