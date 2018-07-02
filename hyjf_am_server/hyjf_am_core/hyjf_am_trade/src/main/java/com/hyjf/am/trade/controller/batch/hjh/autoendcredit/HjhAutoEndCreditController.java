/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch.hjh.autoendcredit;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.HjhAutoEndCreditService;

/**
 * 汇计划自动结束前一天未完全承接完成的债转
 *
 * @author liuyang
 * @version HjhAutoEndCreditController, v0.1 2018/6/28 9:29
 */
@RestController
@RequestMapping("/am-trade/hjhAutoEndCredit")
public class HjhAutoEndCreditController extends BaseController {
    
    @Autowired
    private HjhAutoEndCreditService hjhAutoEndCreditService;

    /**
     * 汇计划自动清算+计算公允价值
     */
    @RequestMapping("/hjhAutoEndCredit")
    public void hjhAutoEndCredit() {
        logger.info("------汇计划自动结束转让定时任务开始------");
        try {
            // 检索投资总的债权转让
            List<HjhDebtCredit> hjhDebtCreditList = this.hjhAutoEndCreditService.selectHjhDebtCreditList();
            // 有未结束债权
            if (CollectionUtils.isNotEmpty(hjhDebtCreditList)) {
                for (int i = 0; i < hjhDebtCreditList.size(); i++) {
                    HjhDebtCredit hjhDebtCredit = hjhDebtCreditList.get(i);
                    hjhAutoEndCreditService.updateHjhDebtCreditStatus(hjhDebtCredit);
                }
            }
            logger.info("------汇计划自动结束转让定时任务结束------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
