package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.RepayReminderSmsNoticeBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: yinhui
 * @Date: 2018/12/18 16:06
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/autoReviewBorrow")
public class AutoReviewBorrowController {

    @Autowired
    private RepayReminderSmsNoticeBatchService repayReminderService;

    @RequestMapping("/taskReviewBorrowAssign")
    public String taskReviewBorrowAssign() {
        repayReminderService.taskReviewBorrowAssign();
        return "success";
    }
}
