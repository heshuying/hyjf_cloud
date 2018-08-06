/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.common.exception.MQException;

/**
 * @author yaoy
 * @version CouponExpiredSmsService, v0.1 2018/6/22 11:44
 */
public interface CouponExpiredSmsService {

    /**
     * 优惠券过期发送短信
     */
    void sendExpiredMsg() throws MQException;
}
