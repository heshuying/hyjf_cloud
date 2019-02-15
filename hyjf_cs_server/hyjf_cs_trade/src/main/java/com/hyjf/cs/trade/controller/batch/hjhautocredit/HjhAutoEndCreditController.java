package com.hyjf.cs.trade.controller.batch.hjhautocredit;

import com.hyjf.cs.trade.controller.batch.BankExceptionController;
import com.hyjf.cs.trade.service.batch.HjhAutoEndCreditService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author xiehuili on 2018/12/18.
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/cs-trade/hjhAutoEndCredit")
public class HjhAutoEndCreditController {

    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private HjhAutoEndCreditService hjhAutoEndCreditService;
    /**
     * 汇计划自动清算+计算公允价值
     * @return
     */
    @ApiOperation(value = "汇计划自动结束转让定时任务", notes = "汇计划自动结束转让定时任务")
    @GetMapping(value = "/hjhAutoEndCredit")
    public String hjhAutoEndCredit() {
        logger.info("汇计划自动结束转让定时任务HjhAutoEndCreditController 开始...");
        hjhAutoEndCreditService.hjhAutoEndCredit();
        logger.info("汇计划自动结束转让定时任务HjhAutoEndCreditController 结束...");
        return "Success";
    }
}
