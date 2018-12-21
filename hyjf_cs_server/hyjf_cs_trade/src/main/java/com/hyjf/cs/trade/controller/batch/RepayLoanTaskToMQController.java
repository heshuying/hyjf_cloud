package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.RepayReminderSmsNoticeBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 批次还款，实时放款批处理任务
 * @Author: yinhui
 * @Date: 2018/12/18 15:45
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/repayLoanTask")
public class RepayLoanTaskToMQController {
    private static final Logger logger = LoggerFactory.getLogger(RepayLoanTaskToMQController.class);

    @Autowired
    private RepayReminderSmsNoticeBatchService repayReminderService;

    @RequestMapping("/taskAssign")
    public String taskAssign() {
        logger.info("【批次还款，实时放款批处理任务定时】开始。。。");
        repayReminderService.taskAssign();
        return "success";
    }
}
