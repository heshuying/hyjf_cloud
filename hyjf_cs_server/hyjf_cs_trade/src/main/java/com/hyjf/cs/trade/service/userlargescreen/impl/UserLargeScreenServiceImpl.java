/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.userlargescreen.impl;

import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;
import com.hyjf.am.vo.api.UserLargeScreenVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import com.hyjf.cs.trade.bean.UserLargeScreenResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.userlargescreen.UserLargeScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: tanyy
 * @version: UserLargeScreenServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl  implements UserLargeScreenService {
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    public AmTradeClient amTradeClient;
    @Override
    public UserLargeScreenResultBean getOnePage(){
        UserLargeScreenResultBean bean = new UserLargeScreenResultBean();
        UserLargeScreenRequest request = new UserLargeScreenRequest();
        String dateString = getNowDateOfDay();
        request.setTaskTime(dateString);
        UserScreenConfigResponse userScreenConfigResponse = amUserClient.getScreenConfig(request);
        UserLargeScreenVO scalePerformanceVo =  amTradeClient.getScalePerformance();
        UserLargeScreenVO monthScalePerformanceListVo =  amTradeClient.getMonthScalePerformanceList();
        UserLargeScreenVO totalAmountVo =  amTradeClient.getTotalAmount();
        UserLargeScreenVO achievementDistributionListVo =  amTradeClient.getAchievementDistributionList();
        UserLargeScreenVO monthReceivedPaymentsVo =  amTradeClient.getMonthReceivedPayments();
        UserLargeScreenVO userCapitalDetailsVo = amTradeClient.getUserCapitalDetails();
       // UserCustomerTaskConfigResponse userCustomerTaskConfigResponse = amUserClient.getCustomerTaskConfig(request);
        ScreenConfigVO vo = userScreenConfigResponse.getResult();
        bean.setNewPassengerGoal(vo.getNewPassengerGoal());
        bean.setOldPassengerGoal(vo.getOldPassengerGoal());
        bean.setScalePerformanceNew(scalePerformanceVo.getScalePerformanceNew());
        bean.setScalePerformanceOld(scalePerformanceVo.getScalePerformanceOld());
        bean.setMonthScalePerformanceListNew(monthScalePerformanceListVo.getMonthScalePerformanceListNew());
        bean.setMonthScalePerformanceListOld(monthScalePerformanceListVo.getMonthScalePerformanceListOld());
        bean.setTotalAmount(totalAmountVo.getTotalAmount());
        bean.setAchievementRate(totalAmountVo.getAchievementRate());
        bean.setAchievementDistribution(totalAmountVo.getAchievementDistribution());
        bean.setAchievementDistributionList(achievementDistributionListVo.getAchievementDistributionList());
        bean.setMonthReceivedPaymentsNew(monthReceivedPaymentsVo.getMonthReceivedPaymentsNew());
        bean.setMonthReceivedPaymentsOld(monthReceivedPaymentsVo.getMonthReceivedPaymentsOld());
        bean.setUserCapitalDetailList(userCapitalDetailsVo.getUserCapitalDetailList());
        return bean;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyyMM
     */
    private  String getNowDateOfDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
