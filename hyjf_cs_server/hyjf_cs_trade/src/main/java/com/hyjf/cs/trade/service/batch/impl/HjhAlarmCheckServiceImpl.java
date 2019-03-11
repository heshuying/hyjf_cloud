package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.HjhAlarmCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/12/18.
 */
@Service
public class HjhAlarmCheckServiceImpl implements HjhAlarmCheckService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 清算日前一天，扫描处于复审中或者投资中的原始标的进行预警
     * @return
     */
    @Override
    public Boolean alermBeforeLiquidateCheck(){
       return amTradeClient.alermBeforeLiquidateCheck();
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     */
    @Override
    public void hjhOpenAccountCheck(){
        amTradeClient.hjhOpenAccountCheck();
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     */
    @Override
    public void hjhOrderExitCheck(){
        amTradeClient.hjhOrderExitCheck();
    }
    /**
     * 订单投资异常短信预警
     * @return
     */
    @Override
    public boolean hjhOrderInvestExceptionCheck(){
        boolean response = amTradeClient.hjhOrderInvestExceptionCheck();
        return response;
    }

    /**
     * 订单投资异常短信预警
     * @return
     */
    @Override
    public void hjhOrderMatchPeriodCheck(){
        amTradeClient.hjhOrderInvestExceptionCheck();
    }
}
