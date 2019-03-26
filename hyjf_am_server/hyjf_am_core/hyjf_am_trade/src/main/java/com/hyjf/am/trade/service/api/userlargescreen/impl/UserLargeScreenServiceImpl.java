/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen.impl;

import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.UserOperateListMapper;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import com.hyjf.am.trade.dao.model.auto.UserOperateList;
import com.hyjf.am.trade.dao.model.auto.UserOperateListExample;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.*;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: tanyy
 * @version: UserLargeScreenServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl extends BaseServiceImpl implements UserLargeScreenService {

    @Autowired
    private AccountListMapper accountListMapper;
    @Autowired
    private UserOperateListMapper userOperateListMapper;

    @Override
    public UserLargeScreenVO getTotalAmount(){
        UserLargeScreenVO vo = userLargeScreenCustomizeMapper.getTotalAmount();
        if(vo==null){
            vo = new UserLargeScreenVO();
        }
        if(vo.getAchievementDistribution().compareTo(BigDecimal.ZERO)>0) {
            vo.setAchievementRate(vo.getTotalAmount().divide(vo.getAchievementDistribution(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
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
    @Override
    public UserLargeScreenVO getUserCapitalDetails(){
        UserLargeScreenVO vo = new UserLargeScreenVO();
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MINUTE,2);*/
        List<UserCapitalDetailsVO> userCapitalDetailsVOList = userLargeScreenCustomizeMapper.getUserCapitalDetails();
        vo.setUserCapitalDetailList(userCapitalDetailsVOList);
        return vo;
    }

    @Override
    public UserLargeScreenTwoVO getDayScalePerformanceList() {
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();
        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getDayScalePerformanceList();
        List<EchartsResultVO> dayScalePerformanceListNew = new ArrayList<>();
        List<EchartsResultVO> dayScalePerformanceListOld = new ArrayList<>();
        for(EchartsResultVO echartsResultVO : list){
            if("1".equals(echartsResultVO.getCustomerGroup())){
                dayScalePerformanceListNew.add(echartsResultVO);
            }else {
                dayScalePerformanceListOld.add(echartsResultVO);
            }
        }
        vo.setDayScalePerformanceListNew(dayScalePerformanceListNew);
        vo.setDayScalePerformanceListOld(dayScalePerformanceListOld);
        return vo;
    }

    @Override
    public UserLargeScreenTwoVO getDayReceivedPayments() {
        List<EchartsResultVO> dayReceivedPaymentsNew = new ArrayList<>();
        List<EchartsResultVO> dayReceivedPaymentsOld = new ArrayList<>();
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getDayReceivedPayments();
        for(EchartsResultVO echartsResultVO:list){
            if("1".equals(echartsResultVO.getCustomerGroup())){
                dayReceivedPaymentsNew.add(echartsResultVO);
            }else {
                dayReceivedPaymentsOld.add(echartsResultVO);
            }
        }
        vo.setDayReceivedPaymentsNew(dayReceivedPaymentsNew);
        vo.setDayReceivedPaymentsOld(dayReceivedPaymentsOld);
        return vo;
    }

    @Override
    public UserLargeScreenTwoVO getMonthDataStatistics() {
        List<MonthDataStatisticsVO> monthDataStatisticsNew = new ArrayList<>();
        List<MonthDataStatisticsVO> monthDataStatisticsOld = new ArrayList<>();
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        // 坐席、年化业绩、充值
        List<MonthDataStatisticsVO> listO =  userLargeScreenCustomizeMapper.getMonthDataStatisticsO();
        // 坐席、提现
        List<MonthDataStatisticsVO> listT =  userLargeScreenCustomizeMapper.getMonthDataStatisticsT();
        // 坐席、回款
        List<MonthDataStatisticsVO> listTh =  userLargeScreenCustomizeMapper.getMonthDataStatisticsTh();
        // 坐席、站岗资金
        List<MonthDataStatisticsVO> listFi =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFi();

        // 坐席、年化业绩、充值
        if(!CollectionUtils.isEmpty(listO)){
            for(MonthDataStatisticsVO monthDataStatisticsVOO : listO){
                if("1".equals(monthDataStatisticsVOO.getCustomerGroup())){
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO monthDataStatisticsVOT : listT){
                            if ("1".equals(monthDataStatisticsVOT.getCustomerGroup()) &&
                                    monthDataStatisticsVOT.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(monthDataStatisticsVOT.getWithdraw());
                            }
                        }
                    }
                    // 站岗资金
                    if(!CollectionUtils.isEmpty(listTh)){
                        for(MonthDataStatisticsVO monthDataStatisticsVOTh : listTh){
                            if ("1".equals(monthDataStatisticsVOTh.getCustomerGroup()) &&
                                    monthDataStatisticsVOTh.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setGuardFund(monthDataStatisticsVOTh.getGuardFund());
                            }
                        }
                    }
                    monthDataStatisticsNew.add(monthDataStatisticsVOO);
                }else {
                    // 回款
                    if(!CollectionUtils.isEmpty(listFi)){
                        for(MonthDataStatisticsVO listFiSon : listFi){
                            if (!"1".equals(listFiSon.getCustomerGroup()) &&
                                    listFiSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setReceived(listFiSon.getReceived());
                            }
                        }
                    }
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO listTSon : listT){
                            if (!"1".equals(listTSon.getCustomerGroup()) &&
                                    listTSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(listTSon.getWithdraw());
                            }
                        }
                    }
                    // 站岗资金
                    if(!CollectionUtils.isEmpty(listTh)){
                        for(MonthDataStatisticsVO listThSon : listTh){
                            if (!"1".equals(listThSon.getCustomerGroup()) &&
                                    listThSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setGuardFund(listThSon.getGuardFund());
                            }
                        }
                    }
                    // 查询每个坐席下的所有用户
                    UserOperateListExample uolexample = new UserOperateListExample();
                    UserOperateListExample.Criteria uolcrt = uolexample.createCriteria();
                    uolcrt.andCurrentOwnerEqualTo(monthDataStatisticsVOO.getCurrentOwner());
                    List<UserOperateList> listFo =  userOperateListMapper.selectByExample(uolexample);
                    List<Integer> userIds = new ArrayList<>();
                    for(UserOperateList listFoSon : listFo){
                        userIds.add(listFoSon.getUserId());
                    }
                    // 获得坐席月初站岗资金
                    AccountListExample alexample = new AccountListExample();
                    AccountListExample.Criteria alcrt = alexample.createCriteria();
                    alcrt.andUserIdIn(userIds);
                    alcrt.andCreateTimeBetween(GetDate.stringToFormatDate(GetDate.getDayStart(GetDate.getFirstDayOfMonthDate()), GetDate.datetimeFormat_key),
                                                    GetDate.stringToFormatDate(GetDate.getDayEnd(GetDate.getFirstDayOfMonthDate()), GetDate.datetimeFormat_key));
                    List<AccountList> accountList = accountListMapper.selectByExample(alexample);
                    // 定义坐席用户总站岗资金
                    BigDecimal monthBeginBalance = new BigDecimal("0");
                    for(AccountList accountListSon : accountList){
                        monthBeginBalance.add(accountListSon.getBankBalance());
                    }
                    // 增资
                    BigDecimal additionalShare = monthDataStatisticsVOO.getRecharge().subtract(monthDataStatisticsVOO.getWithdraw()).add(monthBeginBalance).subtract(monthDataStatisticsVOO.getGuardFund());
                    if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
                        additionalShare = new BigDecimal("0");
                    }
                    monthDataStatisticsVOO.setAdditionalShare(additionalShare);
                    // 提现率
                    BigDecimal extractionRate = new BigDecimal("0");
                    if(monthDataStatisticsVOO.getWithdraw().compareTo(BigDecimal.ZERO) > 0 &&
                            monthDataStatisticsVOO.getReceived().add(monthBeginBalance).compareTo(BigDecimal.ZERO) > 0 ){
                        extractionRate = monthDataStatisticsVOO.getWithdraw().min(monthDataStatisticsVOO.getReceived().add(monthBeginBalance)).divide(monthDataStatisticsVOO.getReceived().add(monthBeginBalance));
                    }
                    monthDataStatisticsVOO.setExtractionRate(extractionRate);

                    monthDataStatisticsOld.add(monthDataStatisticsVOO);
                }
            }
        }

        vo.setMonthDataStatisticsNew(monthDataStatisticsNew);
        vo.setMonthDataStatisticsOld(monthDataStatisticsOld);
        return vo;
    }

    @Override
    public UserLargeScreenTwoVO getOperMonthPerformanceData() {
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();
        OperMonthPerformanceDataVO operMonthPerformanceDataVO = new OperMonthPerformanceDataVO();
        // 运营部-年化业绩、充值
        OperMonthPerformanceDataVO listO =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataO();
        // 运营部-提现
        OperMonthPerformanceDataVO listT =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataT();
        // 运营部-投资
        OperMonthPerformanceDataVO listTh =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataTh();
        // 运营部-回款
        OperMonthPerformanceDataVO listFi =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataFi();
        // 运营部-待回款
        OperMonthPerformanceDataVO listFo =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataFo();
        // 获得坐席月初站岗资金
        BigDecimal startMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:START_BALANCE_"+GetDate.formatDate(new Date(), GetDate.yyyyMM_key), BigDecimal.class);
        // 获得坐席当前站岗资金
        BigDecimal nowMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:NOW_BALANCE_"+ GetDate.formatDate(), BigDecimal.class);
        // 规模业绩
        operMonthPerformanceDataVO.setInvest(listTh.getInvest());
        // 年化业绩
        operMonthPerformanceDataVO.setYearAmount(listO.getYearAmount());
        // 站岗资金
        operMonthPerformanceDataVO.setBalance(nowMonthBalance);
        // 增资
        BigDecimal additionalShare = listO.getRecharge().subtract(listT.getWithdraw()).add(startMonthBalance).subtract(nowMonthBalance);
        if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
            additionalShare = new BigDecimal("0");
        }
        operMonthPerformanceDataVO.setAdditionalShare(additionalShare);
        // 提现
        operMonthPerformanceDataVO.setWithdraw(listT.getWithdraw());
        // 充值
        operMonthPerformanceDataVO.setRecharge(listT.getRecharge());
        // 总待回金额
        operMonthPerformanceDataVO.setAllWaitRepay(listFo.getAllWaitRepay());
        // 已还金额
        operMonthPerformanceDataVO.setAllRepay(listFi.getAllRepay());

        vo.setOperMonthPerformanceData(operMonthPerformanceDataVO);
        return vo;
    }


}
