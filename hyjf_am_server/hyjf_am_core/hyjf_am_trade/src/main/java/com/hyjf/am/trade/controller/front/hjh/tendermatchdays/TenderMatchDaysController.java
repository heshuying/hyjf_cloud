/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.tendermatchdays;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计算自动出借的匹配期(每日)
 * @author liubin
 * @version TenderMatchDaysController, v0.1 2018/8/23 10:56
 */
@RestController
@RequestMapping("/am-trade/tenderMatchDaysController")
public class TenderMatchDaysController extends BaseController {
    @Autowired
    private HjhAccedeService hjhAccedeService;

    /**
     * 计算自动出借的匹配期任务(每日)
     * @return
     */
    @GetMapping("/batch/tenderMatchDays")
    private BooleanResponse tenderMatchDays() {
            logger.info("计算自动出借的匹配期(每日)任务 开始... ");

            try {
                // 更新未进入锁定期的计划订单的匹配期hjhaccede
                if (!this.hjhAccedeService.updateMatchDays()) {
                    logger.error("计算自动出借的匹配期(每日)任务 失败。 ");
                }
            } catch (Exception e) {
                logger.error("计算自动出借的匹配期(每日)任务 异常。 ", e);
                logger.error(e.getMessage());
            }
            logger.info("计算自动出借的匹配期(每日)任务 结束。 ");

        return new BooleanResponse(true);
    }
}
