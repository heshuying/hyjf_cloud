package com.hyjf.am.trade.controller.transactiondemo;

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version TransactionService, v0.1 2018/6/27 14:06
 */
public interface TransactionService {
    void commitAmount(int userId) throws MQException;
    void updateAmount(int userId);
}
