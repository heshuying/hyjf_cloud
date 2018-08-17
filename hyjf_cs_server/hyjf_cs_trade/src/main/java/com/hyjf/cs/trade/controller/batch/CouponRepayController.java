/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.consumer.CouponRepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoyong
 * @version CouponRepayController, v0.1 2018/8/2 14:56
 * 体验金收益期限还款
 */
@RestController
@RequestMapping("/cs-trade/batch/couponPeriodRepay")
public class CouponRepayController extends BaseTradeController {
    @Autowired
    private CouponRepayService couponRepayService;

    @RequestMapping("/periodRepay")
    public void couponRepay() {
        logger.info("体验金收益期限还款 开始");
        try {
            List<String> recoverNidList = couponRepayService.selectNidForCouponOnly();
            if (!CollectionUtils.isEmpty(recoverNidList)) {
                couponRepayService.couponOnlyRepay(recoverNidList);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("体验金收益期限还款 结束");
    }
}
