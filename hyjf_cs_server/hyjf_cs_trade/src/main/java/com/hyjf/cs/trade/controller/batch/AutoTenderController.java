/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.AutoTenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自动投资
 * @author liubin
 * @version AutoTenderController, v0.1 2018/6/28 13:59
 */
@Controller
@RequestMapping(value = "/batch/tender")
public class AutoTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(AutoTenderController.class);
    @Autowired
    private AutoTenderService autoTenderService;

    @RequestMapping("/autotender")
    public void AutoTender() {
        logger.info("自动投资任务开始start...");
        autoTenderService.AutoTender();
        logger.info("自动投资任务结束end...");
    }
}
