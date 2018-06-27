/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;
import com.hyjf.cs.trade.service.RepayDataRecoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoy
 * @version RepayDataRecoverController, v0.1 2018/6/25 10:56
 * 优惠券还款掉单修复定时任务
 */
@RestController
@RequestMapping("batch/coupon")
public class RepayDataRecoverController {
    private static final Logger logger = LoggerFactory.getLogger(RepayDataRecoverController.class);
    @Autowired
    private RepayDataRecoverService repayDataRecoverService;

    @RequestMapping("/dataRecover")
    public void dataRecover() {
        logger.info("优惠券还款掉单数据修复开始...............................");
        try {
            List<String> recoverNidList = new ArrayList<String>();
            recoverNidList.add("HFD17050404");
            recoverNidList.add("NEW17060480");
            recoverNidList.add("NEW17060479");
            recoverNidList.add("HDD17050314");
            recoverNidList.add("NEW17060389");
            recoverNidList.add("HDD17050316");
            recoverNidList.add("HDD17050315");
            recoverNidList.add("HDD17050318");
            recoverNidList.add("NEW17060387");
            recoverNidList.add("HDD17050313");
            recoverNidList.add("NEW17060388");
            recoverNidList.add("HXD160706701");
            recoverNidList.add("HXD160706702");

            for (String borrowNid : recoverNidList) {
                List<CouponTenderCustomizeVO> couponTenderList = repayDataRecoverService.getCouponTenderList(borrowNid);

                for (CouponTenderCustomizeVO ct : couponTenderList) {
                    try {
                        logger.info("优惠券还款掉单数据修复, 项目编号：" + borrowNid + " 优惠券投资编号：" + ct.getOrderId());
                        repayDataRecoverService.couponRepayDataRecover(borrowNid, 1, ct);
                    } catch (Exception e) {
                        logger.info("直投类优惠券还款数据修复失败，优惠券投资编号：" + ct.getOrderId() + " 项目编号：" + borrowNid);
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        logger.info("优惠券还款掉单数据修复结束............................................");
    }

}
