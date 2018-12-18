package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.RepayReminderSmsNoticeBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 产品加息放款、还款批处理任务
 * @Author: yinhui
 * @Date: 2018/12/18 16:11
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/increaseinterest")
public class IncreaseinterestLoansController {

    @Autowired
    private RepayReminderSmsNoticeBatchService repayReminderService;

    /**
     * 产品加息放款批处理任务
     * @return
     */
    @RequestMapping("/taskAssignLoans")
    public String taskAssignLoans() {
        repayReminderService.taskAssignLoans();
        return "success";
    }

    /**
     * 产品加息还款批处理任务
     * @return
     */
    @RequestMapping("/taskAssignRepay")
    public String taskAssignRepay() {
        repayReminderService.taskAssignRepay();
        return "success";
    }
}
