/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

/**
 * @author yaoyong
 * @version TyjCouponRepayService, v0.1 2018/8/13 11:55
 */
public interface TyjCouponRepayService {
    /**
     * 体验金按收益期限还款
     * @param recoverNid
     * @return
     */
    void updateCouponOnlyRecover(String recoverNid) throws Exception;
}
