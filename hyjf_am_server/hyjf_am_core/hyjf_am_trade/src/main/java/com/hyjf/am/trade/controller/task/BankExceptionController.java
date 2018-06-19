package com.hyjf.am.trade.controller.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.trade.service.BankRechargeService;

import io.swagger.annotations.Api;

@Api(value = "江西银行充值掉单异常处理定时任务")
@RestController
@RequestMapping("/am-trade/bankException")
public class BankExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(BankExceptionController.class);

    @Autowired
    private BankRechargeService bankRechargeExceptionService;

    @RequestMapping("/recharge")
    public void recharge(){
        logger.info("开始江西银行充值掉单异常处理...");
        bankRechargeExceptionService.recharge();
    }

}