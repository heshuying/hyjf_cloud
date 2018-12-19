package com.hyjf.cs.trade.controller.batch.autoreview;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.controller.batch.BankExceptionController;
import com.hyjf.cs.trade.service.batch.BatchBankInvestService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author xiehuili
 * @date 20181218
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/cs-trade/batch/hjhautoreview")
public class BatchAutoReviewHjhController  extends BaseTradeController {


    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private BatchBankInvestService batchBankInvestService;
    /**
     * 自动放款复审任务
     * @return
     */
    @ApiOperation(value = "自动放款复审任务", notes = "自动放款复审任务")
    @GetMapping(value = "/hjhautoreview")
    public String autoReview() {
        logger.info("自动放款复审任务BatchAutoReviewHjhController开始...");
        batchBankInvestService.hjhautoreview();
        logger.info("自动放款复审任务BatchAutoReviewHjhController结束...");
        return "SUCCESS";
    }
}
