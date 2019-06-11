/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service;

import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;

/**
 * @author wangjun
 * @version DuiBaOrderService, v0.1 2019/6/10 10:36
 */
public interface DuiBaOrderService {
    /**
     * 根据兑吧订单号查询兑吧订单
     * @param duiBaOrderId
     * @return
     */
    int countDuiBaOrder(String duiBaOrderId);

    /**
     * 生成兑吧订单
     * @param consumeParams
     */
    void insertDuiBaOrder(CreditConsumeParams consumeParams);
}
