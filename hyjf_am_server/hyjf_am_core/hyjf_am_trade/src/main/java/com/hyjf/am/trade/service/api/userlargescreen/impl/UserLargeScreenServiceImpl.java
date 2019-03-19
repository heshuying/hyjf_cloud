/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen.impl;

import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.EchartsResultVO;
import com.hyjf.am.vo.api.UserLargeScreenVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: tanyy
 * @version: UserLargeScreenServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl extends BaseServiceImpl implements UserLargeScreenService {


    @Override
    public UserLargeScreenVO getTotalAmount(){
        UserLargeScreenVO vo = userLargeScreenCustomizeMapper.getTotalAmount();
        if(vo==null){
            vo = new UserLargeScreenVO();
        }
        if(vo.getAchievementDistribution().compareTo(BigDecimal.ZERO)>0) {
            vo.setAchievementRate(vo.getTotalAmount().divide(vo.getAchievementDistribution()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return vo;
    }
    @Override
    public UserLargeScreenVO getMonthScalePerformanceList(){
        UserLargeScreenVO vo = new UserLargeScreenVO();
        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getMonthScalePerformanceList();
        List<EchartsResultVO> monthScalePerformanceListNew = new ArrayList<>();
        List<EchartsResultVO> monthScalePerformanceListOld = new ArrayList<>();
        for(EchartsResultVO echartsResultVO:list){
            if("1".equals(echartsResultVO.getCustomerGroup())){
                monthScalePerformanceListNew.add(echartsResultVO);
            }else {
                monthScalePerformanceListOld.add(echartsResultVO);
            }
        }
        vo.setMonthScalePerformanceListNew(monthScalePerformanceListNew);
        vo.setMonthScalePerformanceListOld(monthScalePerformanceListOld);
        return vo;
    }
    @Override
    public UserLargeScreenVO getScalePerformance(){
        UserLargeScreenVO vo = userLargeScreenCustomizeMapper.getScalePerformance();
        if(vo==null){
            vo = new UserLargeScreenVO();
        }
        return vo;
    }
    @Override
    public UserLargeScreenVO getAchievementDistributionList(){
        UserLargeScreenVO vo = new UserLargeScreenVO();
        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getAchievementDistributionList();
        vo.setAchievementDistributionList(list);
        return vo;
    }
    @Override
    public UserLargeScreenVO  getMonthReceivedPayments(){
        UserLargeScreenVO vo = new UserLargeScreenVO();
        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getMonthReceivedPayments();
        List<EchartsResultVO> monthReceivedPaymentsNew = new ArrayList<>();
        List<EchartsResultVO> monthReceivedPaymentsOld = new ArrayList<>();
        for(EchartsResultVO echartsResultVO:list){
            if("1".equals(echartsResultVO.getCustomerGroup())){
                monthReceivedPaymentsNew.add(echartsResultVO);
            }else {
                monthReceivedPaymentsOld.add(echartsResultVO);
            }
        }
        vo.setMonthReceivedPaymentsNew(monthReceivedPaymentsNew);
        vo.setMonthReceivedPaymentsOld(monthReceivedPaymentsOld);
        return vo;
    }


}
