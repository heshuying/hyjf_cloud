/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.common.exception.MQException;

/**
 * @author yaoy
 * @version CouponExpiredPushService, v0.1 2018/6/19 15:35
 */
public interface CouponExpiredPushService {
    void sendExpiredMsgAct() throws MQException;
}
