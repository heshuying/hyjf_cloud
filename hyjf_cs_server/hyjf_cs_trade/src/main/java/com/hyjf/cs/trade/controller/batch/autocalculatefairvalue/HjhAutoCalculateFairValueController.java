package com.hyjf.cs.trade.controller.batch.autocalculatefairvalue;

import com.hyjf.cs.trade.controller.batch.BankExceptionController;
import com.hyjf.cs.trade.service.batch.HjhAutoCalculateFairValueService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 汇计划自动计算计划订单公允价值
 * @author xiehuili on 2018/12/18.
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/cs-trade/hjhAutoCalculateFairValue")
public class HjhAutoCalculateFairValueController {
    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private HjhAutoCalculateFairValueService service;
    /**
     * 汇计划自动计算计划订单公允价值
     * @return
     */
    @ApiOperation(value = "汇计划自动计算计划订单公允价值", notes = "汇计划自动计算计划订单公允价值")
    @GetMapping(value = "/hjhCalculateFairValue")
    public String hjhCalculateFairValue() {
        logger.info("汇计划自动计算计划订单公允价值HjhAutoCalculateFairValueController开始...");
        service.hjhCalculateFairValue();
        logger.info("汇计划自动计算计划订单公允价值HjhAutoCalculateFairValueController结束...");
        return "SUCCESS";
    }


}
