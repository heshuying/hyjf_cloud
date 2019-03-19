/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen;

import com.hyjf.am.vo.api.UserLargeScreenVO;

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



}
