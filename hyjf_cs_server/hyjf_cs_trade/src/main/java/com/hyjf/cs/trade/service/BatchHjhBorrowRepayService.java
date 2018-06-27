/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayService, v0.1 2018/6/25 15:33
 */
public interface BatchHjhBorrowRepayService extends BaseTradeService {

    /**
     * 获取借款人相应的银行账号
     *
     * @param userId
     * @return
     */
//    BankOpenAccount getBankOpenAccount(Integer userId);

    /**
     * 退出计划
     * @param accedeOrderId
     */
    void updateQuitRepayInfo(String accedeOrderId);

    /**
     * 锁定计划
     * @param accedeOrderId
     */
    void updateLockRepayInfo(String accedeOrderId);
}
