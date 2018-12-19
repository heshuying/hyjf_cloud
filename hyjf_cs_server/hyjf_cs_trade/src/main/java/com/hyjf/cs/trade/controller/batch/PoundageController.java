package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.PoundageService;
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
 * 手续费分账明细插入定时
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/cs-trade/batch")
public class PoundageController {


    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private PoundageService poundageService;
    /**
     *手续费分账明细插入定时
     * @return
     */
    @ApiOperation(value = "手续费分账明细插入定时", notes = "手续费分账明细插入定时")
    @GetMapping(value = "/poundage")
    public String poundage() {
        logger.info("手续费分账明细插入定时PoundageController开始...");
        poundageService.poundage();
        logger.info("手续费分账明细插入定时PoundageController结束...");
        return "SUCCESS";
    }

}
