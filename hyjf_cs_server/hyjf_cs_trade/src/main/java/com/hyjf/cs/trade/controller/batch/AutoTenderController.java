/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.batch.AutoTenderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 自动投资
 *
 * @author liubin
 * @version AutoTenderController, v0.1 2018/6/28 13:59
 */
@Api(tags = "自动投资Batch接口")
@Controller
@RequestMapping(value = "/batch/tender")
public class AutoTenderController extends BaseTradeController {
    @Autowired
    private AutoTenderService autoTenderService;

    @RequestMapping("/autotender")
    public Boolean AutoTender() {
        logger.info("自动投资任务开始start...");
        boolean flag = false;

        // 取得自动投资用加入计划列表（改用消息队列要防止重复拉去）
        List<HjhAccedeVO> hjhAccedes = this.autoTenderService.selectPlanJoinList();

        if (hjhAccedes == null) {
            logger.error("汇计划自动投资任务 结束... （hjhAccedes=null） ");
            return flag;
        }

        logger.info("汇计划自动投资任务 取得加入计划订单数" + hjhAccedes.size());

        for (HjhAccedeVO hjhAccede : hjhAccedes) {
            try {
                String logMsgHeader = "======计划：" + hjhAccede.getPlanNid()
                        + "的计划订单号：" + hjhAccede.getAccedeOrderId();
                logger.info(logMsgHeader + " 计划自动投资开始。");

                flag = this.autoTenderService.AutoTenderForOneAccede(hjhAccede);
                if (!flag) {
                    logger.info(logMsgHeader + " 计划自动投资结束。投资失败！！！");
                } else {
                    logger.info(logMsgHeader + " 计划自动投资结束。投资成功！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("自动投资任务结束end...");
        return flag;
    }


}
