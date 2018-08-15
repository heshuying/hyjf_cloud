/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.vo.trade.HjhLockVo;

/**
 * @author wangjun
 * @version PlanLockQuitService, v0.1 2018/7/17 16:21
 */
public interface PlanLockQuitService {
    /**
     * 计划锁定
     * @param hjhLockVo
     */
    void updateLockRepayInfo(HjhLockVo hjhLockVo);

    /**
     * 计划退出
     * @param accedeOrderId
     */
    void updateQuitRepayInfo(String accedeOrderId);
}
