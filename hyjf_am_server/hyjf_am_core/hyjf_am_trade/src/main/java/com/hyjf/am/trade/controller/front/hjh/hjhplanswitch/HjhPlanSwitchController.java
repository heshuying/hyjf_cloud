/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.hjhplanswitch;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 汇计划开关
 * @author liubin
 * @version HjhPlanSwitchController, v0.1 2018/8/14 16:41
 */
@RestController
@RequestMapping("/am-trade/hjhPlanSwitchController")
public class HjhPlanSwitchController extends BaseController {
    @Autowired
    private HjhPlanService hjhPlanService;
    /**
     * 定时开启计划任务
     *
     * @return
     */
    @GetMapping("/batch/hjhPlanJoinOn")
    private BooleanResponse hjhPlanJoinOn() {
        logger.info("定时开启计划任务 开始... ");

        try {
            // 开启计划
            int result = this.hjhPlanService.updateHjhPlanForJoinSwitch(1);
            if (result == 0) {
                logger.info("没有开启的计划。");
            }else{
                logger.info("开启了 " + result + " 个计划。");
            }
        } catch (Exception e) {
            logger.error("定时开启计划任务 异常... ", e);
        }
        logger.info("定时开启计划任务 结束... ");
        return new BooleanResponse(true);
    }

    /**
     * 定时关闭计划任务
     *
     * @return
     */
    @GetMapping("/batch/hjhPlanJoinOff")
    private BooleanResponse hjhPlanJoinOff() {
        logger.info("定时关闭计划任务 开始... ");

        try {
            // 关闭计划
            int result = this.hjhPlanService.updateHjhPlanForJoinSwitch(2);
            if (result == 0) {
                logger.info("没有关闭的计划。");
            }else{
                logger.info("关闭了 " + result + " 个计划。");
            }
        } catch (Exception e) {
            logger.error("定时关闭计划任务 异常... ", e);
        }
        logger.info("定时关闭计划任务 结束... ");
        return new BooleanResponse(true);
    }
}
