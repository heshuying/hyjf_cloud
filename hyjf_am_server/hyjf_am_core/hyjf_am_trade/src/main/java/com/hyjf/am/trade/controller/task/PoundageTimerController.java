/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import com.hyjf.am.trade.service.task.PoundageTimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version PoundageController, v0.1 2018/6/21 15:41
 */
@RestController
@RequestMapping("/am-trade/batch")
public class PoundageTimerController {
    private static final Logger logger = LoggerFactory.getLogger(PoundageTimerController.class);

    @Autowired
    PoundageTimerService poundageTimerService;

    @RequestMapping("/poundage")
    public void poundageTimerTask() {
        logger.info("手续费分享明细插入开始");
        poundageTimerService.insertPoundage();
        logger.info("手续费分享明细插入结束");
    }
}
