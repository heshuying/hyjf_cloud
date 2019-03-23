/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.api.*;

import java.util.List;

/**
 * @author tanyy
 * @version UserLargeScreenCustomizeMapper, v0.1 2018/7/12 11:02
 */
public interface UserLargeScreenCustomizeMapper {

    UserLargeScreenVO getTotalAmount();

    UserLargeScreenVO getScalePerformance();

    List<EchartsResultVO> getAchievementDistributionList();

    List<EchartsResultVO> getMonthScalePerformanceList();

    List<EchartsResultVO> getMonthReceivedPayments();

    List<UserCapitalDetailsVO> getUserCapitalDetails();

    List<EchartsResultVO> getDayScalePerformanceList();

    List<EchartsResultVO> getDayReceivedPayments();

    List<MonthDataStatisticsVO> getMonthDataStatisticsO();

    List<MonthDataStatisticsVO> getMonthDataStatisticsT();

    List<MonthDataStatisticsVO> getMonthDataStatisticsTh();

    List<MonthDataStatisticsVO> getMonthDataStatisticsFi();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataO();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataT();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataTh();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataFi();
}
