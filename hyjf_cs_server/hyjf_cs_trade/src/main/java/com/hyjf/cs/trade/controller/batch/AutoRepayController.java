package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.RepayReminderSmsNoticeBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 自动还款请求
 * @Author: yinhui
 * @Date: 2018/12/18 15:51
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/autoRepay")
public class AutoRepayController {

    private static final Logger logger = LoggerFactory.getLogger(AutoRepayController.class);

    @Autowired
    private RepayReminderSmsNoticeBatchService repayReminderService;

    @RequestMapping("/taskRepayAssign")
    public boolean taskRepayAssign() {
        logger.info("【自动还款请求定时】开始。。。");

        repayReminderService.taskRepayAssign();
        return true;
    }
}
