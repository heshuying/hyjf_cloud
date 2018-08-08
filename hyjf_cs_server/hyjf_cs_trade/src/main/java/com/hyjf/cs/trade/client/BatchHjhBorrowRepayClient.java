/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClient, v0.1 2018/6/25 17:40
 */
public interface BatchHjhBorrowRepayClient {

    /**
     * 计划锁定
     *
     * @param accedeOrderId
     */
    void updateForLock(String accedeOrderId);

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    void updateForQuit(String accedeOrderId);
}

