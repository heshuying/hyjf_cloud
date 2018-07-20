/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

/**
 * @author wangjun
 * @version PlanLockQuitService, v0.1 2018/7/17 16:21
 */
public interface PlanLockQuitService {
    /**
     * 计划锁定
     * @param accedeOrderId
     */
    void updateLockRepayInfo(String accedeOrderId);

    /**
     * 计划退出
     * @param accedeOrderId
     */
    void updateQuitRepayInfo(String accedeOrderId);
}
