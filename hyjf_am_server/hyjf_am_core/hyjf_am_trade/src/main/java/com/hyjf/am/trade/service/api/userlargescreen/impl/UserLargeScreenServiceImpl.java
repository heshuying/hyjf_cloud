/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen.impl;

import com.hyjf.am.trade.dao.mapper.auto.ScreenTwoParamMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.trade.dao.model.auto.ScreenTwoParamExample;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.*;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: tanyy
 * @version: UserLargeScreenServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl extends BaseServiceImpl implements UserLargeScreenService {

    private static final Logger logger = LoggerFactory.getLogger(UserLargeScreenServiceImpl.class);

    @Autowired
    private AccountListCustomizeMapper accountListCustomizeMapper;
    @Autowired
    private ScreenTwoParamMapper screenTwoParamMapper;

    @Override
    public UserLargeScreenVO getTotalAmount(){
        UserLargeScreenVO vo = userLargeScreenCustomizeMapper.getTotalAmount();
        if(vo==null){
            vo = new UserLargeScreenVO();
        }
       /* if(vo.getAchievementDistribution().compareTo(BigDecimal.ZERO)>0) {
            vo.setAchievementRate(vo.getTotalAmount().divide(vo.getAchievementDistribution(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }*/
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
        if(!CollectionUtils.isEmpty(list)) {
            for (EchartsResultVO echartsResultVO : list) {
                if ("1".equals(echartsResultVO.getCustomerGroup())) {
                    monthReceivedPaymentsNew.add(echartsResultVO);
                } else {
                    monthReceivedPaymentsOld.add(echartsResultVO);
                }
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

    /**
     * 屏幕二本月数据统计(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getMonthDataStatistics() {
        List<MonthDataStatisticsVO> monthDataStatisticsNew = new ArrayList<>();
        List<MonthDataStatisticsVO> monthDataStatisticsOld = new ArrayList<>();
        List<MonthDataStatisticsVO> currentOwnersAndUserIds = null;
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        // 查询当天batch统计数据
        ScreenTwoParamExample example = new ScreenTwoParamExample();
        ScreenTwoParamExample.Criteria criteria = example.createCriteria();
        criteria.andQueryTimeEqualTo(new Date());
        List<ScreenTwoParam> screenTwoParams = screenTwoParamMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(screenTwoParams)){
            // 对象数据转移
            currentOwnersAndUserIds = new ArrayList();
            for (ScreenTwoParam param : screenTwoParams) {
                MonthDataStatisticsVO monthDataStatisticsVO = new MonthDataStatisticsVO();
                monthDataStatisticsVO.setCustomerGroup(param.getFlag().toString());
                monthDataStatisticsVO.setCurrentOwner(param.getCustomerName());
                monthDataStatisticsVO.setGuardFund(param.getNowBalance());
                monthDataStatisticsVO.setAdditionalShare(param.getCapitalIncrease());
                monthDataStatisticsVO.setExtractionRate(param.getCashWithdrawalRate());
                currentOwnersAndUserIds.add(monthDataStatisticsVO);
            }
            // 坐席、年化业绩
            List<MonthDataStatisticsVO> listFo =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFo();
            // 坐席、充值
            List<MonthDataStatisticsVO> listO =  userLargeScreenCustomizeMapper.getMonthDataStatisticsO();
            // 坐席、提现
            List<MonthDataStatisticsVO> listT =  userLargeScreenCustomizeMapper.getMonthDataStatisticsT();
            // 新、老客组数据处理
            for(MonthDataStatisticsVO monthDataStatisticsVOO : currentOwnersAndUserIds){
                if("1".equals(monthDataStatisticsVOO.getCustomerGroup())){
                    // 年化业绩
                    if(!CollectionUtils.isEmpty(listFo)){
                        for(MonthDataStatisticsVO listFoSon : listFo){
                            if ("1".equals(listFoSon.getCustomerGroup()) &&
                                    listFoSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setYearMoney(listFoSon.getYearMoney());
                            }
                        }
                    }
                    // 充值
                    if(!CollectionUtils.isEmpty(listO)){
                        for(MonthDataStatisticsVO listOSon : listO){
                            if ("1".equals(listOSon.getCustomerGroup()) &&
                                    listOSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setRecharge(listOSon.getRecharge());
                            }
                        }
                    }
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO monthDataStatisticsVOT : listT){
                            if ("1".equals(monthDataStatisticsVOT.getCustomerGroup()) &&
                                    monthDataStatisticsVOT.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(monthDataStatisticsVOT.getWithdraw());
                            }
                        }
                    }
                    logger.info("新客组{}月坐席下用户的 年化业绩:{},充值:{},提现:{},当前站岗资金:{}",
                            monthDataStatisticsVOO.getCurrentOwner(),
                            monthDataStatisticsVOO.getYearMoney(),
                            monthDataStatisticsVOO.getRecharge(),
                            monthDataStatisticsVOO.getWithdraw(),
                            monthDataStatisticsVOO.getGuardFund());

                    // 新客组数据
                    monthDataStatisticsNew.add(monthDataStatisticsVOO);
                }else if("2".equals(monthDataStatisticsVOO.getCustomerGroup())) {
                    // 年化业绩
                    if(!CollectionUtils.isEmpty(listFo)){
                        for(MonthDataStatisticsVO listFoSon : listFo){
                            if ("2".equals(listFoSon.getCustomerGroup()) &&
                                    listFoSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setYearMoney(listFoSon.getYearMoney());
                            }
                        }
                    }
                    // 充值
                    if(!CollectionUtils.isEmpty(listO)){
                        for(MonthDataStatisticsVO listOSon : listO){
                            if ("2".equals(listOSon.getCustomerGroup()) &&
                                    listOSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setRecharge(listOSon.getRecharge());
                            }
                        }
                    }
                    // 提现
                    if(!CollectionUtils.isEmpty(listT)){
                        for(MonthDataStatisticsVO listTSon : listT){
                            if ("2".equals(listTSon.getCustomerGroup()) &&
                                    listTSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setWithdraw(listTSon.getWithdraw());
                            }
                        }
                    };
                    logger.info("老客组{}月坐席下用户的 年化业绩:{},充值:{},提现:{},当前站岗资金:{},增资:{},提现率:{}",
                            monthDataStatisticsVOO.getCurrentOwner(),
                            monthDataStatisticsVOO.getYearMoney(),
                            monthDataStatisticsVOO.getRecharge(),
                            monthDataStatisticsVOO.getWithdraw(),
                            monthDataStatisticsVOO.getGuardFund(),
                            monthDataStatisticsVOO.getAdditionalShare(),
                            monthDataStatisticsVOO.getExtractionRate());

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
     * 屏幕二运营部月度业绩数据
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
        // 查询当天batch统计数据
        ScreenTwoParamExample example = new ScreenTwoParamExample();
        ScreenTwoParamExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(3);
        criteria.andQueryTimeEqualTo(new Date());
        List<ScreenTwoParam> screenTwoParams = screenTwoParamMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(screenTwoParams)){
            ScreenTwoParam param = screenTwoParams.get(0);
            // 当前站岗资金
            operMonthPerformanceDataVO.setBalance(param.getNowBalance());
            // 增资
            operMonthPerformanceDataVO.setAdditionalShare(param.getCapitalIncrease());
        }
        // 规模业绩
        operMonthPerformanceDataVO.setInvest(listTh.getInvest());
        // 年化业绩
        operMonthPerformanceDataVO.setYearAmount(listSi.getYearAmount());
        // 提现
        operMonthPerformanceDataVO.setWithdraw(listT.getWithdraw().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 充值
        operMonthPerformanceDataVO.setRecharge(listO.getRecharge().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 总待回金额
        operMonthPerformanceDataVO.setAllWaitRepay(listFo.getAllWaitRepay());
        // 已回金额
        operMonthPerformanceDataVO.setAllRepay(listFi.getAllRepay());
        logger.info("运营部的 规模业绩:{},年化业绩:{},当前站岗资金:{},增资:{},提现:{},充值:{},总待回金额:{},已回金额:{}",
                operMonthPerformanceDataVO.getInvest(),
                operMonthPerformanceDataVO.getYearAmount(),
                operMonthPerformanceDataVO.getBalance(),
                operMonthPerformanceDataVO.getAdditionalShare(),
                operMonthPerformanceDataVO.getWithdraw(),
                operMonthPerformanceDataVO.getRecharge(),
                operMonthPerformanceDataVO.getAllWaitRepay(),
                operMonthPerformanceDataVO.getAllRepay());

        vo.setOperMonthPerformanceData(operMonthPerformanceDataVO);
        return vo;
    }

    @Override
    public List<ScreenTransferVO> getAllScreenUser(int start, int sizes) {
        Map<String,Object> param = new HashMap<>();
        param.put("start",start);
        param.put("sizes",sizes);
        return userLargeScreenCustomizeMapper.getAllScreenUser(param);
    }

    @Override
    public void updateOperatieList(List<ScreenTransferVO> updateList) {
         userLargeScreenCustomizeMapper.updateOperatieList(updateList);
    }

    @Override
    public void deleteOperatieList(List<ScreenTransferVO> deleteList) {
        userLargeScreenCustomizeMapper.deleteOperatieList(deleteList);
    }

    @Override
    public void updateRepaymentPlan(List<ScreenTransferVO> updateList) {
        userLargeScreenCustomizeMapper.updateRepaymentPlan(updateList);
    }

    @Override
    public void deleteRepaymentPlan(List<ScreenTransferVO> deleteList) {
        userLargeScreenCustomizeMapper.deleteRepaymentPlan(deleteList);
    }
}
