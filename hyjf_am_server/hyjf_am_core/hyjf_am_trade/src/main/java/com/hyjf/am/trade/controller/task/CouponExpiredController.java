/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import com.hyjf.am.trade.service.CouponExpiredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoy
 * @version CouponExpiredController, v0.1 2018/6/19 10:22
 * 优惠劵过期提醒
 */
@RestController
@RequestMapping("/batch/coupon")
public class CouponExpiredController {
    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredController.class);

    @Autowired
    private CouponExpiredService couponExpiredService;

    @RequestMapping("/expired")
    public void updateCouponExpired() {
        logger.info("检查优惠券使用是否过期 开始");
        couponExpiredService.updateCouponExpired();
        logger.info("检查优惠券使用是否过期 结束");
    }
}
