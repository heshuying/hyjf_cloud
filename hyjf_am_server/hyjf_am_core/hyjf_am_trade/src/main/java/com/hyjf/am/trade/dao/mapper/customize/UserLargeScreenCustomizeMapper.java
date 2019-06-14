/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.api.*;
import com.hyjf.am.vo.screen.ScreenTransferVO;

import java.util.List;
import java.util.Map;

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

    List<UserCapitalDetailsVO> getUserCapitalDetails(Map<String, Object> param);

    List<EchartsResultVO> getDayScalePerformanceList();

    List<EchartsResultVO> getDayReceivedPayments();

    List<MonthDataStatisticsVO> getMonthDataStatisticsO();

    List<MonthDataStatisticsVO> getMonthDataStatisticsT();

    List<MonthDataStatisticsVO> getMonthDataStatisticsFo();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataO();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataT();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataTh();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataFi();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataFo();

    OperMonthPerformanceDataVO getOperMonthPerformanceDataSi();

    /**
     * @Author walter.limeng
     * @Description //获取投屏采集到的所有的用户
     * @Date 15:45 2019-05-29
     * @Param [param]
     * @return java.util.List<com.hyjf.am.vo.screen.ScreenTransferVO>
     **/
    List<ScreenTransferVO> getAllScreenUser(Map<String, Object> param);

    /**
     * @Author walter.limeng
     * @Description //投屏划转对ht_user_operate_list表执行更新操作
     * @Date 17:26 2019-05-29
     * @Param [updateList]
     * @return java.util.List<com.hyjf.am.vo.screen.ScreenTransferVO>
     **/
    void updateOperatieList(List<ScreenTransferVO> updateList);

    /**
     * @Author walter.limeng
     * @Description //投屏划转对ht_user_operate_list表执行删除操作
     * @Date 17:48 2019-05-29
     * @Param [deleteList]
     * @return void
     **/
    void deleteOperatieList(List<ScreenTransferVO> deleteList);

    /**
     * @Author walter.limeng
     * @Description //投屏划转对ht_repayment_plan表执行更新操作
     * @Date 17:26 2019-05-29
     * @Param [updateList]
     * @return java.util.List<com.hyjf.am.vo.screen.ScreenTransferVO>
     **/
    void updateRepaymentPlan(List<ScreenTransferVO> updateList);

    /**
     * @Author walter.limeng
     * @Description //投屏划转对ht_repayment_plan表执行删除操作
     * @Date 17:48 2019-05-29
     * @Param [deleteList]
     * @return void
     **/
    void deleteRepaymentPlan(List<ScreenTransferVO> deleteList);
}
