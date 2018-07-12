package com.hyjf.admin.service.exception;

import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeService, v0.1 2018/7/11 15:16
 */
public interface BankRepayFreezeService {
    Integer getFreezeLogCount();

    List<BankRepayFreezeLogVO> getFreezeLogList(Integer limitStart, Integer limitEnd);

    BankRepayFreezeLogVO getFreezeLogByOrderId(String orderId);

    boolean updateBankRepayFreeze(BankRepayFreezeLogVO freezeLogVO);

    boolean repayUnfreeze(BankRepayFreezeLogVO repayFreezeFlog);
}
