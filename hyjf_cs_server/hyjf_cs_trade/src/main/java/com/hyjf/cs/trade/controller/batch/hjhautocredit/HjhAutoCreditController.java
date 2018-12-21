package com.hyjf.cs.trade.controller.batch.hjhautocredit;

import com.hyjf.cs.trade.controller.batch.BankExceptionController;
import com.hyjf.cs.trade.service.batch.HjhAutoCreditService;
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
@RequestMapping(value = "/cs-trade/hjhAutoCredit")
public class HjhAutoCreditController {

    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private HjhAutoCreditService hjhAutoCreditService;
    /**
     * 汇计划自动清算
     * @return
     */
    @ApiOperation(value = "汇计划自动清算", notes = "汇计划自动清算+计算公允价值")
    @GetMapping(value = "/hjhAutoCredit")
    public String hjhAutoCredit() {
        logger.info("汇计划自动清算HjhAutoCreditController 开始...");
        hjhAutoCreditService.hjhAutoCredit();
        logger.info("汇计划自动清算HjhAutoCreditController 结束...");
        return "Success";
    }


}
