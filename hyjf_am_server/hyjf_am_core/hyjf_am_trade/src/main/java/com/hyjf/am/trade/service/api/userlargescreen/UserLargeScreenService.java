/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen;

import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import com.hyjf.am.vo.api.UserLargeScreenVO;
import com.hyjf.am.vo.screen.ScreenTransferVO;

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
    UserLargeScreenTwoVO getMonthDataStatistics();

    /**
     * 屏幕二运营部月度业绩数据
     * @return
     */
    UserLargeScreenTwoVO getOperMonthPerformanceData();

    /**
     * @Author walter.limeng
     * @Description //获取投屏采集到的所有的数据
     * @Date 15:42 2019-05-29
     * @Param [start, sizes]
     * @return java.util.List<com.hyjf.am.vo.screen.ScreenTransferVO>
     **/
    List<ScreenTransferVO> getAllScreenUser(int start, int sizes);

    /**
     * @Author walter.limeng
     * @Description //投屏划转对ht_user_operate_list表执行更新操作
     * @Date 17:25 2019-05-29
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
     * @Date 17:25 2019-05-29
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
