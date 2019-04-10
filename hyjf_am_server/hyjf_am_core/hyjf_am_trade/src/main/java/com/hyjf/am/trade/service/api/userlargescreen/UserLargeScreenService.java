/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen;

import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import com.hyjf.am.vo.api.UserLargeScreenVO;

import java.util.List;

/**
 * @author tanyy
 * @version UserLargeScreenService, v0.1 2019/3/18 14:28
 */
public interface UserLargeScreenService {

    /**
     * 规模业绩
     * @return UserLargeScreenVO
     **/
    UserLargeScreenVO getScalePerformance();

    /**
     * 坐席月规模业绩
     * @return UserLargeScreenVO
     **/
    UserLargeScreenVO getMonthScalePerformanceList();
    /**
     * 运营部总业绩（元）和 本月业绩完成率
     * @return UserLargeScreenVO
     **/
    UserLargeScreenVO getTotalAmount();
    /**
     * 本月运营部业绩完成分布 - 饼图返回
     * @return UserLargeScreenVO
     **/
    UserLargeScreenVO getAchievementDistributionList();

    /**
     * 坐席月回款情况
     * @return UserLargeScreenVO
     **/
    UserLargeScreenVO  getMonthReceivedPayments();

    UserLargeScreenVO getUserCapitalDetails();

    /**
     * 屏幕二日业绩(新客组、老客组)
     * @return
     */
    UserLargeScreenTwoVO getDayScalePerformanceList();

    /**
     * 屏幕二日回款(新客组、老客组)
     * @return
     */
    UserLargeScreenTwoVO getDayReceivedPayments();

    /**
     * 屏幕二本月数据统计(新客组、老客组)
     * @return
     */
    UserLargeScreenTwoVO getMonthDataStatistics(List<MonthDataStatisticsVO> currentOwnersAndUserIds);

    /**
     * 屏幕二运营部月度业绩数据
     * @return
     */
    UserLargeScreenTwoVO getOperMonthPerformanceData();
}
