package com.hyjf.am.trade.dao.mapper.customize;

import java.util.Map;

/**
 * @Author : huanghui
 */
public interface FundChangeStatisticsCustomizeMapper {

    /**
     * 统计指定时间段充值
     * @param params
     * @return
     */
    Integer countRechargeMoney(Map<String, Object> params);

    /**
     * 统计出借金额
     * @param params
     * @return
     */
    Integer countInvestmentMoney(Map<String, Object> params);

    /**
     * 统计债权出借金额
     * @param params
     * @return
     */
    Integer countInvestmentCreditTenderMoney(Map<String, Object> params);

    /**
     * 汇计划加入资金金额
     * @param params
     * @return
     */
    Integer countInvestmentHjhCreditTenderMoney(Map<String, Object> params);

    /**
     * 上线以来所有出借金额
     * @return
     */
    Integer countAllAccount();

    /**
     * 统计出借人数
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
