package com.hyjf.am.trade.controller.exception.bankrecharge;

import com.hyjf.am.trade.service.BankRechargeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "江西银行充值掉单异常处理定时任务")
@RestController
@RequestMapping("/bankRechargeException")
public class BankRechargeExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(BankRechargeExceptionController.class);

    @Autowired
    private BankRechargeService bankRechargeExceptionService;

    @RequestMapping("/recharge")
    public void recharge(){
        logger.info("开始江西银行充值掉单异常处理...");
        bankRechargeExceptionService.recharge();
    }

}