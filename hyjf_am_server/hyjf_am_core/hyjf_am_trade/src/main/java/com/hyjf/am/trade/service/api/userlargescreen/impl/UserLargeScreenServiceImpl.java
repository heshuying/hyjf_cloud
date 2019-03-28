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

    /**
     * 屏幕二日业绩(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getDayScalePerformanceList() {
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();
        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getDayScalePerformanceList();
        List<EchartsResultVO> dayScalePerformanceListNew = new ArrayList<>();
        List<EchartsResultVO> dayScalePerformanceListOld = new ArrayList<>();
        for(EchartsResultVO echartsResultVO : list){
            BigDecimal money = new BigDecimal(echartsResultVO.getMoney());
            if("1".equals(echartsResultVO.getCustomerGroup())){
                echartsResultVO.setMoney(money.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                dayScalePerformanceListNew.add(echartsResultVO);
            }else {
                echartsResultVO.setMoney(money.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                dayScalePerformanceListOld.add(echartsResultVO);
            }
        }
        vo.setDayScalePerformanceListNew(dayScalePerformanceListNew);
        vo.setDayScalePerformanceListOld(dayScalePerformanceListOld);
        return vo;
    }

    /**
     * 屏幕二日回款(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getDayReceivedPayments() {
        List<EchartsResultVO> dayReceivedPaymentsNew = new ArrayList<>();
        List<EchartsResultVO> dayReceivedPaymentsOld = new ArrayList<>();
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        List<EchartsResultVO> list =  userLargeScreenCustomizeMapper.getDayReceivedPayments();
        for(EchartsResultVO echartsResultVO : list){
            BigDecimal money = new BigDecimal(echartsResultVO.getMoney());
            BigDecimal money2 = new BigDecimal(echartsResultVO.getMoney());
            if("1".equals(echartsResultVO.getCustomerGroup())){
                echartsResultVO.setMoney(money.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                echartsResultVO.setMoney2(money2.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                dayReceivedPaymentsNew.add(echartsResultVO);
            }else {
                echartsResultVO.setMoney(money.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                echartsResultVO.setMoney2(money2.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                dayReceivedPaymentsOld.add(echartsResultVO);
            }
        }
        vo.setDayReceivedPaymentsNew(dayReceivedPaymentsNew);
        vo.setDayReceivedPaymentsOld(dayReceivedPaymentsOld);
        return vo;
    }

    /**
     * 本月数据统计(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getMonthDataStatistics() {
        List<MonthDataStatisticsVO> monthDataStatisticsNew = new ArrayList<>();
        List<MonthDataStatisticsVO> monthDataStatisticsOld = new ArrayList<>();
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        // 坐席、充值
        List<MonthDataStatisticsVO> listO =  userLargeScreenCustomizeMapper.getMonthDataStatisticsO();
        // 坐席、提现
        List<MonthDataStatisticsVO> listT =  userLargeScreenCustomizeMapper.getMonthDataStatisticsT();
        // 坐席、回款
        List<MonthDataStatisticsVO> listFi =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFi();
        // 坐席、年化业绩
        List<MonthDataStatisticsVO> listFo =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFo();

        // 坐席、年化业绩、充值
        if(!CollectionUtils.isEmpty(listO)){
            for(MonthDataStatisticsVO monthDataStatisticsVOO : listO){
                if("1".equals(monthDataStatisticsVOO.getCustomerGroup())){
                    // 查询每个坐席下的所有用户
                    UserOperateListExample uolexample = new UserOperateListExample();
                    UserOperateListExample.Criteria uolcrt = uolexample.createCriteria();
                    uolcrt.andCurrentOwnerEqualTo(monthDataStatisticsVOO.getCurrentOwner());
                    List<UserOperateList> userOperateList =  userOperateListMapper.selectByExample(uolexample);
                    List<Integer> userIds = new ArrayList<>();
                    for(UserOperateList listSing : userOperateList){
                        userIds.add(listSing.getUserId());
                    }
                    // 坐席下用户当前站岗资金
                    BigDecimal monthNowBalance = new BigDecimal("0");
                    AccountListExample alexample = new AccountListExample();
                    AccountListExample.Criteria alcrt = alexample.createCriteria();
                    alcrt.andUserIdIn(userIds);
                    alcrt.andCreateTimeLessThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayEnd(new Date()), GetDate.datetimeFormat_key));
                    List<AccountList> accountList = accountListMapper.selectByExample(alexample);
                    for(AccountList accountListSon : accountList){
                        monthNowBalance.add(accountListSon.getBankBalance());
                    }
                    // 年化业绩
                    if(!CollectionUtils.isEmpty(listFo)){
                        for(MonthDataStatisticsVO listFoSon : listFo){
                            if ("1".equals(listFoSon.getCustomerGroup()) &&
                                    listFoSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setYearMoney(listFoSon.getYearMoney().setScale(0, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO monthDataStatisticsVOT : listT){
                            if ("1".equals(monthDataStatisticsVOT.getCustomerGroup()) &&
                                    monthDataStatisticsVOT.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(monthDataStatisticsVOT.getWithdraw().setScale(0, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    // 站岗资金
                    monthDataStatisticsVOO.setGuardFund(monthNowBalance.setScale(0, BigDecimal.ROUND_HALF_UP));

                    // 新客组数据
                    monthDataStatisticsNew.add(monthDataStatisticsVOO);
                }else {
                    // 年化业绩
                    if(!CollectionUtils.isEmpty(listFo)){
                        for(MonthDataStatisticsVO listFoSon : listFo){
                            if (!"1".equals(listFoSon.getCustomerGroup()) &&
                                    listFoSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setYearMoney(listFoSon.getYearMoney().setScale(0, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO listTSon : listT){
                            if (!"1".equals(listTSon.getCustomerGroup()) &&
                                    listTSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(listTSon.getWithdraw().setScale(0, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    // 回款
                    if(!CollectionUtils.isEmpty(listFi)){
                        for(MonthDataStatisticsVO listFiSon : listFi){
                            if (!"1".equals(listFiSon.getCustomerGroup()) &&
                                    listFiSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setReceived(listFiSon.getReceived().setScale(0, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    // 查询每个坐席下的所有用户
                    UserOperateListExample uolexample = new UserOperateListExample();
                    UserOperateListExample.Criteria uolcrt = uolexample.createCriteria();
                    uolcrt.andCurrentOwnerEqualTo(monthDataStatisticsVOO.getCurrentOwner());
                    List<UserOperateList> userOperateList =  userOperateListMapper.selectByExample(uolexample);
                    List<Integer> userIds = new ArrayList<>();
                    for(UserOperateList listSing : userOperateList){
                        userIds.add(listSing.getUserId());
                    }
                    // 坐席下用户当前站岗资金
                    BigDecimal monthNowBalance = new BigDecimal("0");
                    AccountListExample alexample = new AccountListExample();
                    AccountListExample.Criteria alcrt = alexample.createCriteria();
                    alcrt.andUserIdIn(userIds);
                    alcrt.andCreateTimeLessThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayEnd(new Date()), GetDate.datetimeFormat_key));
                    List<AccountList> accountList = accountListMapper.selectByExample(alexample);
                    for(AccountList accountListSon : accountList){
                        monthNowBalance.add(accountListSon.getBankBalance());
                    }
                    // 坐席下用户月初站岗资金
                    BigDecimal monthBeginBalance = new BigDecimal("0");
                    alcrt.andCreateTimeLessThanOrEqualTo(GetDate.stringToFormatDate(GetDate.getDayEnd(GetDate.getFirstDayOfMonthDate()), GetDate.datetimeFormat_key));
                    List<AccountList> accountListT = accountListMapper.selectByExample(alexample);
                    for(AccountList accountListTSon : accountListT){
                        monthBeginBalance.add(accountListTSon.getBankBalance());
                    }
                    // 站岗资金
                    monthDataStatisticsVOO.setGuardFund(monthNowBalance.setScale(0, BigDecimal.ROUND_HALF_UP));
                    // 增资
                    BigDecimal additionalShare = monthDataStatisticsVOO.getRecharge().subtract(monthDataStatisticsVOO.getWithdraw()).add(monthBeginBalance).subtract(monthNowBalance);
                    if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
                        additionalShare = new BigDecimal("0");
                    }
                    monthDataStatisticsVOO.setAdditionalShare(additionalShare.setScale(0, BigDecimal.ROUND_HALF_UP));
                    // 提现率
                    BigDecimal extractionRate = new BigDecimal("0");
                    if(monthDataStatisticsVOO.getWithdraw().compareTo(BigDecimal.ZERO) > 0 &&
                            monthDataStatisticsVOO.getReceived().add(monthBeginBalance).compareTo(BigDecimal.ZERO) > 0 ){
                        extractionRate = monthDataStatisticsVOO.getWithdraw().min(monthDataStatisticsVOO.getReceived().add(monthBeginBalance)).divide(monthDataStatisticsVOO.getReceived().add(monthBeginBalance), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    monthDataStatisticsVOO.setExtractionRate(extractionRate);

                    // 老客组数据
                    monthDataStatisticsOld.add(monthDataStatisticsVOO);
                }
            }
        }

        vo.setMonthDataStatisticsNew(monthDataStatisticsNew);
        vo.setMonthDataStatisticsOld(monthDataStatisticsOld);
        return vo;
    }

    /**
     * 运营部月度业绩数据
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getOperMonthPerformanceData() {
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();
        OperMonthPerformanceDataVO operMonthPerformanceDataVO = new OperMonthPerformanceDataVO();
        // 运营部-充值
        OperMonthPerformanceDataVO listO =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataO();
        // 运营部-提现
        OperMonthPerformanceDataVO listT =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataT();
        // 运营部-投资
        OperMonthPerformanceDataVO listTh =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataTh();
        // 运营部-回款
        OperMonthPerformanceDataVO listFi =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataFi();
        // 运营部-待回款
        OperMonthPerformanceDataVO listFo =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataFo();
        // 运营部-年化业绩
        OperMonthPerformanceDataVO listSi =  userLargeScreenCustomizeMapper.getOperMonthPerformanceDataSi();
        // 获得坐席月初站岗资金
        BigDecimal startMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:START_BALANCE_"+GetDate.formatDate(new Date(), GetDate.yyyyMM_key), BigDecimal.class);
        // 获得坐席当前站岗资金
        BigDecimal nowMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:NOW_BALANCE_"+ GetDate.formatDate(), BigDecimal.class);
        // 规模业绩
        operMonthPerformanceDataVO.setInvest(listTh.getInvest().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 年化业绩
        operMonthPerformanceDataVO.setYearAmount(listSi.getYearAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 站岗资金
        operMonthPerformanceDataVO.setBalance(nowMonthBalance.setScale(0, BigDecimal.ROUND_HALF_UP));
        // 增资
        BigDecimal additionalShare = listO.getRecharge().subtract(listT.getWithdraw()).add(startMonthBalance).subtract(nowMonthBalance);
        if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
            additionalShare = new BigDecimal("0");
        }
        operMonthPerformanceDataVO.setAdditionalShare(additionalShare.setScale(0, BigDecimal.ROUND_HALF_UP));
        // 提现
        operMonthPerformanceDataVO.setWithdraw(listT.getWithdraw().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 充值
        operMonthPerformanceDataVO.setRecharge(listO.getRecharge().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 总待回金额
        operMonthPerformanceDataVO.setAllWaitRepay(listFo.getAllWaitRepay().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 已回金额
        operMonthPerformanceDataVO.setAllRepay(listFi.getAllRepay().setScale(0, BigDecimal.ROUND_HALF_UP));

        vo.setOperMonthPerformanceData(operMonthPerformanceDataVO);
        return vo;
    }


}
