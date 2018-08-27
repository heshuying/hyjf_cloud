/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.service.batch.CouponExpiredPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoy
 * @version CouponExpiredPushController, v0.1 2018/6/19 15:21
 * 优惠券过期发送push消息定时任务
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/couponExpiredPush")
public class CouponExpiredPushController {
    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredPushController.class);

    @Autowired
    private CouponExpiredPushService couponExpiredPushService;

    @RequestMapping("/expiredPush")
    public void sendCouponExpiredMsg() {
        logger.info("优惠券过期检查发送push消息开始");
        try {
            couponExpiredPushService.sendExpiredMsgAct();
        } catch (MQException e) {
            logger.info("优惠券过期检查发送push消息失败");
        }
    }
}
