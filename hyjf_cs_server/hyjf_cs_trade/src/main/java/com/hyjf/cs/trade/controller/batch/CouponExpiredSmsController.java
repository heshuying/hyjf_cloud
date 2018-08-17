/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.service.batch.CouponExpiredSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoy
 * @version CouponExpiredSmsController, v0.1 2018/6/22 11:31
 * 优惠券过期短信提醒
 */
@RestController
@RequestMapping("/cs-trade/batch/couponExpiredSms")
public class CouponExpiredSmsController {

    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredSmsController.class);
    @Autowired
    private CouponExpiredSmsService couponExpiredSmsService;

    @RequestMapping("/expiredSms")
    public void sendExpiredMsg() {
        logger.info("优惠券过期短信提醒开始");
        try {
            couponExpiredSmsService.sendExpiredMsg();
        } catch (MQException e) {
            logger.error("优惠券过期短信失败...", e);
        }
    }
}
