/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.userlargescreen.impl;

import com.hyjf.am.trade.dao.mapper.customize.AccountListCustomizeMapper;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: tanyy
 * @version: UserLargeScreenServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl extends BaseServiceImpl implements UserLargeScreenService {

    private static final Logger logger = LoggerFactory.getLogger(UserLargeScreenServiceImpl.class);

    @Autowired
    private AccountListCustomizeMapper accountListCustomizeMapper;

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
    public UserLargeScreenTwoVO getMonthDataStatistics(List<MonthDataStatisticsVO> currentOwnersAndUserIds) {
        List<MonthDataStatisticsVO> monthDataStatisticsNew = new ArrayList<>();
        List<MonthDataStatisticsVO> monthDataStatisticsOld = new ArrayList<>();
        UserLargeScreenTwoVO vo = new UserLargeScreenTwoVO();

        // 坐席、年化业绩
        List<MonthDataStatisticsVO> listFo =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFo();
        // 坐席、充值
        List<MonthDataStatisticsVO> listO =  userLargeScreenCustomizeMapper.getMonthDataStatisticsO();
        // 坐席、提现
        List<MonthDataStatisticsVO> listT =  userLargeScreenCustomizeMapper.getMonthDataStatisticsT();
        // 坐席、回款
        List<MonthDataStatisticsVO> listFi =  userLargeScreenCustomizeMapper.getMonthDataStatisticsFi();

        if(!CollectionUtils.isEmpty(currentOwnersAndUserIds)){
            for(MonthDataStatisticsVO monthDataStatisticsVOO : currentOwnersAndUserIds){
                if("1".equals(monthDataStatisticsVOO.getCustomerGroup())){
                    // 得到每个坐席下的所有用户
                    List<Integer> userIds = monthDataStatisticsVOO.getUserIds();
                    // 计算月坐席下用户的当前站岗资金
                    BigDecimal monthNowBalance = BigDecimal.ZERO;
                    if (!CollectionUtils.isEmpty(userIds)){
                        // 每个集合数据量
                        int num = 3;
                        // 计算分多少个集合
                        int listNum = userIds.size()%num == 0 ? userIds.size()/num : userIds.size()/num+1;
                        // 储存userId的大list切分成多个小list,防止sql过长
                        List<List<Integer>> usersIdLists = averageAssign(userIds, listNum);
                        // 计算月坐席下用户的当前站岗资金
                        for (List<Integer> usersIdList : usersIdLists) {
                            monthNowBalance = monthNowBalance.add(accountListCustomizeMapper.getUsersMonthNowBalance(usersIdList));
                        }
                    }
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
                                monthDataStatisticsVOO.setYearMoney(listFoSon.getYearMoney());
                            }
                        }
                    }
                    // 充值
                    if(!CollectionUtils.isEmpty(listO)){
                        for(MonthDataStatisticsVO listOSon : listO){
                            if (!"1".equals(listOSon.getCustomerGroup()) &&
                                    listOSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setRecharge(listOSon.getRecharge());
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
                    // 回款
                    if(!CollectionUtils.isEmpty(listFi)){
                        for(MonthDataStatisticsVO listFiSon : listFi){
                            if (!"1".equals(listFiSon.getCustomerGroup()) &&
                                    listFiSon.getCurrentOwner().equals(monthDataStatisticsVOO.getCurrentOwner())){
                                monthDataStatisticsVOO.setReceived(listFiSon.getReceived());
                            }
                        }
                    }
                    // 得到每个坐席下的所有用户
                    List<Integer> userIds = monthDataStatisticsVOO.getUserIds();
                    // 坐席下用户月初站岗资金
                    BigDecimal monthBeginBalance = BigDecimal.ZERO;
                    // 坐席下用户当前站岗资金
                    BigDecimal monthNowBalance = BigDecimal.ZERO;
                    if (!CollectionUtils.isEmpty(userIds)){
                        // 每个集合数据量
                        int num = 3;
                        // 计算分多少个集合
                        int listNum = userIds.size()%num == 0 ? userIds.size()/num : userIds.size()/num+1;
                        // 储存userId的大list切分成多个小list,防止sql过长
                        List<List<Integer>> usersIdLists = averageAssign(userIds, listNum);

                        if(RedisUtils.exists("USER_LARGE_SCREEN_TWO_MONTH:MONTH_BEGIN_BALANCE_"+getPingYin(monthDataStatisticsVOO.getCurrentOwner())+"_"+ GetDate.formatDate(new Date(), GetDate.yyyyMM_key))){
                            monthBeginBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:MONTH_BEGIN_BALANCE_"+getPingYin(monthDataStatisticsVOO.getCurrentOwner())+"_"+ GetDate.formatDate(new Date(), GetDate.yyyyMM_key), BigDecimal.class);
                        }else {
                            // 计算月坐席下用户的月初站岗资金
                            for (List<Integer> usersIdList : usersIdLists) {
                                monthBeginBalance = monthBeginBalance.add(accountListCustomizeMapper.getUsersMonthBeginBalance(usersIdList));
                            }
                            RedisUtils.setObjEx("USER_LARGE_SCREEN_TWO_MONTH:MONTH_BEGIN_BALANCE_"+getPingYin(monthDataStatisticsVOO.getCurrentOwner())+"_"+ GetDate.formatDate(new Date(), GetDate.yyyyMM_key), monthBeginBalance, 31 * 24 * 60 * 60);
                        }

                        // 计算月坐席下用户的当前站岗资金
                        for (List<Integer> usersIdList : usersIdLists) {
                            monthNowBalance = monthNowBalance.add(accountListCustomizeMapper.getUsersMonthNowBalance(usersIdList));
                        }
                    }
                    // 当前站岗资金
                    monthDataStatisticsVOO.setGuardFund(monthNowBalance.setScale(0, BigDecimal.ROUND_HALF_UP));
                    // 增资
                    BigDecimal additionalShare = monthDataStatisticsVOO.getRecharge().subtract(monthDataStatisticsVOO.getWithdraw()).add(monthBeginBalance).subtract(monthNowBalance);
                    if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
                        logger.info("老客组{}月坐席下用户的增资是:{},小于等于0,默认为0", monthDataStatisticsVOO.getCurrentOwner(), additionalShare);
                        additionalShare = BigDecimal.ZERO;
                    }
                    monthDataStatisticsVOO.setAdditionalShare(additionalShare.setScale(0, BigDecimal.ROUND_HALF_UP));
                    // 提现率
                    BigDecimal extractionRate = BigDecimal.ZERO;
                    if(monthDataStatisticsVOO.getWithdraw().compareTo(BigDecimal.ZERO) > 0 &&
                            monthDataStatisticsVOO.getReceived().add(monthBeginBalance).compareTo(BigDecimal.ZERO) > 0 ){
                        extractionRate = monthDataStatisticsVOO.getWithdraw().min(monthDataStatisticsVOO.getReceived().add(monthBeginBalance)).divide(monthDataStatisticsVOO.getReceived().add(monthBeginBalance), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    monthDataStatisticsVOO.setExtractionRate(extractionRate);
                    logger.info("老客组{}月坐席下用户的充值:{},提现:{},月初站岗资金:{},当前站岗资金:{},回款:{}", monthDataStatisticsVOO.getCurrentOwner(), monthDataStatisticsVOO.getRecharge(), monthDataStatisticsVOO.getWithdraw(), monthBeginBalance, monthNowBalance, monthDataStatisticsVOO.getReceived());

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
        // 获得坐席当前站岗资金
        BigDecimal nowMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:NOW_BALANCE_"+ GetDate.formatDate(), BigDecimal.class);
        if(nowMonthBalance == null){
            nowMonthBalance = BigDecimal.ZERO;
        }
        // 获得坐席月初站岗资金
        BigDecimal startMonthBalance = RedisUtils.getObj("USER_LARGE_SCREEN_TWO_MONTH:START_BALANCE_"+GetDate.formatDate(new Date(), GetDate.yyyyMM_key), BigDecimal.class);
        if (startMonthBalance == null){
            startMonthBalance = BigDecimal.ZERO;
        }
        logger.info("运营部的充值:{},提现:{},月初站岗资金:{},当前站岗资金:{}", listO.getRecharge(), listT.getWithdraw(), startMonthBalance, nowMonthBalance);
        // 规模业绩
        operMonthPerformanceDataVO.setInvest(listTh.getInvest());
        // 年化业绩
        operMonthPerformanceDataVO.setYearAmount(listSi.getYearAmount());
        // 站岗资金
        operMonthPerformanceDataVO.setBalance(nowMonthBalance.setScale(0, BigDecimal.ROUND_HALF_UP));
        // 增资
        BigDecimal additionalShare = listO.getRecharge().subtract(listT.getWithdraw()).add(startMonthBalance).subtract(nowMonthBalance);
        if(additionalShare.compareTo(BigDecimal.ZERO) <= 0){
            additionalShare = BigDecimal.ZERO;
        }
        operMonthPerformanceDataVO.setAdditionalShare(additionalShare.setScale(0, BigDecimal.ROUND_HALF_UP));
        // 提现
        operMonthPerformanceDataVO.setWithdraw(listT.getWithdraw().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 充值
        operMonthPerformanceDataVO.setRecharge(listO.getRecharge().setScale(0, BigDecimal.ROUND_HALF_UP));
        // 总待回金额
        operMonthPerformanceDataVO.setAllWaitRepay(listFo.getAllWaitRepay());
        // 已回金额
        operMonthPerformanceDataVO.setAllRepay(listFi.getAllRepay());

        vo.setOperMonthPerformanceData(operMonthPerformanceDataVO);
        return vo;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result = new ArrayList<List<T>>();
        //(先计算出余数)
        int remaider = source.size()%n;
        //然后是商
        int number = source.size()/n;
        //偏移量
        int offset = 0;
        for(int i=0; i<n; i++){
            List<T> value = null;
            if(remaider > 0){
                value = source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 得到中文全拼
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }
}
