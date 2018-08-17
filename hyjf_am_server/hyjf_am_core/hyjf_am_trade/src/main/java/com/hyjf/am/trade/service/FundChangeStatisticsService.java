package com.hyjf.am.trade.service;

import java.util.Map;

/**
 * 资金变化统计 Service
 * @Author : huanghui
 */
public interface FundChangeStatisticsService {

    /**
     * 统计指定时间段内充值金额
     * @param params
     * @return
     */
    Integer countRechargeMoney(Map<String, Object> params);

    /**
     * 统计投资金额
     * @return
     */
    Integer countInvestmentMoney(Map<String, Object> params);

    /**
     * 统计债权投资金额
     * @param params
     * @return
     */
    Integer countInvestmentCreditTenderMoney(Map<String, Object> params);

    /**
     * 汇计划加入资金统计
     * @param params
     * @return
     */
    Integer countInvestmentHjhCreditTenderMoney(Map<String, Object> params);

    /**
     * 上线以来所有投资金额
     * @return
     */
    Integer countAllAccount();

    /**
     * 统计投资人数
     * @param params
     * @return
     */
    Integer getNumberOfInvestors(Map<String, Object> params);

    /**
     * 今日新增充值次数
     * @param params
     * @return
     */
    Integer countTodayNewRechargeNum(Map<String, Object> params);
}
