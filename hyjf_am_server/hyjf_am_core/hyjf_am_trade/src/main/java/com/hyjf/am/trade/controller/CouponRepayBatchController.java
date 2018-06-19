/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.trade.service.CouponRepayBatchService;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayBatchController, v0.1 2018/6/15 17:31
 * 体验金按收益期限还款
 */
@RestController
@RequestMapping("/batch/coupon")
public class CouponRepayBatchController {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayBatchController.class);

    @Autowired
    private CouponRepayBatchService couponRepayBatchService;

    @RequestMapping("/periodRepay")
    public void couponRepay() {
        logger.info("筛选优惠券单独投资还款开始");
        try {
            List<String> recoverNidList = couponRepayBatchService.selectNidForCouponOnly();
            if (recoverNidList != null) {
                logger.info("需按优惠券单独投资还款包括：" + JSONArray.toJSONString(recoverNidList));
                CommonSoaUtils.couponOnlyRepay(recoverNidList);
            }
        } catch (Exception e) {
            logger.error("筛选优惠券单独投资还款失败", e);
        }
        logger.info("筛选优惠券单独投资还款结束");
    }

}
