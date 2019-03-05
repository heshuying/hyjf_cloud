package com.hyjf.cs.trade.controller.batch.hjhalerm;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.controller.batch.BankExceptionController;
import com.hyjf.cs.trade.service.batch.HjhAlarmCheckService;
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
@RequestMapping(value = "/cs-trade/hjhAlarmController/batch")
public class HjhAlermCheckController extends BaseTradeController {

    Logger logger = LoggerFactory.getLogger(BankExceptionController.class);
    @Autowired
    private HjhAlarmCheckService hjhAlarmService;
    /**
     * 清算日前一天，扫描处于复审中或者投资中的原始标的进行预警
     * @return
     */
    @ApiOperation(value = "清算日前一天，扫描处于复审中或者投资中的原始标的进行预警", notes = "清算日前一天，扫描处于复审中或者投资中的原始标的进行预警")
    @GetMapping(value = "/alermBeforeLiquidateCheck")
    public String alermBeforeLiquidateCheck() {
        logger.info("清算日前一天，扫描处于复审中或者投资中的原始标的进行预警HjhAlermBeforeLiquidateCheckControllerstart...");
        hjhAlarmService.alermBeforeLiquidateCheck();
        logger.info("清算日前一天，扫描处于复审中或者投资中的原始标的进行预警HjhAlermBeforeLiquidateCheckControllerend...");
        return "Success";
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     * @return
     */
    @ApiOperation(value = "汇计划各计划开放额度校验预警任务", notes = "汇计划各计划开放额度校验预警任务")
    @GetMapping(value = "/hjhOpenAccountCheck")
    public void hjhOpenAccountCheck() {
        logger.info("汇计划各计划开放额度校验预警任务 开始... ");
        hjhAlarmService.hjhOpenAccountCheck();
        logger.info("汇计划各计划开放额度校验预警任务 结束... ");
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     * @return
     */
    @ApiOperation(value = "汇计划各计划开放额度校验预警任务", notes = "汇计划各计划开放额度校验预警任务")
    @GetMapping(value = "/hjhOrderExitCheck")
    public Boolean hjhOrderExitCheck() {
        logger.info("订单退出超过两天邮件预警 开始... ");
        hjhAlarmService.hjhOrderExitCheck();
        logger.info("订单退出超过两天邮件预警 结束... ");
        return true;
    }

    /**
     * 订单投资异常短信预警
     * @return
     */
    @ApiOperation(value = "订单投资异常短信预警", notes = "订单投资异常短信预警")
    @GetMapping(value = "/hjhOrderInvestExceptionCheck")
    public Boolean hjhOrderInvestExceptionCheck() {
        logger.info("订单投资异常短信预警 开始... ");
        boolean response = false;
        try {
            response = hjhAlarmService.hjhOrderInvestExceptionCheck();
        } catch (Exception e) {
            logger.error("订单投资异常短信预警 异常 e :" + e);
        }
        logger.info("订单投资异常短信预警 结束... ");
        return response;
    }
    /**
     * 订单投资异常短信预警
     * @return
     */
    @ApiOperation(value = "hjh订单匹配期超过两天短信预警", notes = "hjh订单匹配期超过两天短信预警")
    @GetMapping(value = "/hjhOrderMatchPeriodCheck")
    public Boolean hjhOrderMatchPeriodCheck() {
        logger.info("hjh订单匹配期超过两天短信预警 开始... ");
        hjhAlarmService.hjhOrderMatchPeriodCheck();
        logger.info("hjh订单匹配期超过两天短信预警 结束... ");
        return true;
    }



}
