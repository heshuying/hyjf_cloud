package com.hyjf.cs.trade.service.batch;

/**
 * @author xiehuili on 2018/12/18.
 */
public interface HjhAlarmCheckService {

    /**
     * 清算日前一天，扫描处于复审中或者投资中的原始标的进行预警
     * @return
     */
    Boolean alermBeforeLiquidateCheck();
    /**
     * 汇计划各计划开放额度校验预警任务
     */
    public void hjhOpenAccountCheck();

    /**
     * 汇计划各计划开放额度校验预警任务
     * @return
     */
    public void hjhOrderExitCheck();

    /**
     * 订单投资异常短信预警
     * @return
     */
    public boolean hjhOrderInvestExceptionCheck();

    /**
     * hjh订单匹配期超过两天短信预警
     * @return
     */
    public void hjhOrderMatchPeriodCheck();
}
