/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.CouponRepayStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoy
 * @version CouponRepayStatisticController, v0.1 2018/6/20 14:50
 * 加息券还款统计定时任务
 */
@RestController
@RequestMapping("/batch/repayStatistic")
public class CouponRepayStatisticController {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayStatisticController.class);

    @Autowired
    private CouponRepayStatisticService couponRepayStatisticService;

    @RequestMapping("/couponRepayStatistic")
    public void updateCouponRepayStatistic() {
        logger.info("加息券还款统计开始...");
        couponRepayStatisticService.updateOrSaveCouponStatistic();
    }
}
