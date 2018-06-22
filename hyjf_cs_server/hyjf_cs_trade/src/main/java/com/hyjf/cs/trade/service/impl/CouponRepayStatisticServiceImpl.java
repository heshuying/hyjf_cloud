/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.CouponRepayMonitorVO;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.CouponRepayMonitorClient;
import com.hyjf.cs.trade.client.HolidaysConfigClient;
import com.hyjf.cs.trade.service.CouponRepayStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author yaoy
 * @version CouponRepayStatisticServiceImpl, v0.1 2018/6/19 14:35
 */
@Service
public class CouponRepayStatisticServiceImpl implements CouponRepayStatisticService {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayStatisticServiceImpl.class);

    public static final String DATE_FORMAT = "yyyy年MM月dd日";

    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private CouponRepayMonitorClient couponRepayMonitorClient;
    @Autowired
    private HolidaysConfigClient holidaysConfigClient;

    /**
     * 自动生成加息券每日收益统计数据
     */
    @Override
    public int updateOrSaveCouponStatistic() {
        int result = 0;

        //计算开始时间
        Calendar calendarStart = this.getStatisticStartTime();
        long timeStart = calendarStart.getTime().getTime()/1000;

        //计算结束时间
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());
        calendarEnd.add(Calendar.DAY_OF_MONTH, 3);
        this.getStatisticEndTime(calendarEnd);
        long timeEnd = calendarEnd.getTime().getTime()/1000;

        //统计待收收益
//        Map<String,Object> paraMap = new HashMap<String,Object>();
//        paraMap.put("timeStart", timeStart);
//        paraMap.put("timeEnd", timeEnd);

        List<CouponRecoverCustomizeVO> interestWaitList = amTradeClient.selectCouponInterestWaitToday(timeStart,timeEnd);
        interestWaitList = processInterestWaitList(interestWaitList, calendarEnd);

        //统计已收收益
        BigDecimal interestReceived = amTradeClient.selectCouponInterestReceivedToday(timeStart,timeEnd);
        if(interestReceived == null){
            interestReceived = BigDecimal.ZERO;
        }

        List<BigDecimal> interestWaitFinal = getInterestWaitListFinal(interestWaitList);

        for(int i=0; i<interestWaitFinal.size(); i++) {
            Calendar cc = this.getStatisticStartTime();
            cc.add(Calendar.DAY_OF_MONTH, i);
            String nowDay = GetDate.formatDate(cc.getTime());
            String nowWeek = GetDate.getWeekOfDate(cc.getTime());

            if(i > 0){
                interestReceived = BigDecimal.ZERO;
            }

//            CouponRepayMonitorExample example = new CouponRepayMonitorExample();
//            CouponRepayMonitorExample.Criteria criteria = example.createCriteria();
//            criteria.andDayEqualTo(nowDay);
            List<CouponRepayMonitorVO> monitors = couponRepayMonitorClient.selectCouponRepayMonitor(nowDay);

            if(monitors == null || monitors.isEmpty()){
                //新增
                CouponRepayMonitorVO monitor = new CouponRepayMonitorVO();
                monitor.setDay(nowDay);
                monitor.setWeek(nowWeek);
                monitor.setInterestWaitTotal(interestWaitFinal.get(i));
                monitor.setInterestYesTotal(interestReceived);
                monitor.setAddTime(GetDate.getNowTime10());
                monitor.setAddUser(CustomConstants.USERID_ADMIN);
                monitor.setUpdateTime(GetDate.getNowTime10());
                monitor.setUpdateUser(CustomConstants.USERID_ADMIN);
                monitor.setDelFlg(0);
                result = couponRepayMonitorClient.insertCouponRepayMonitor(monitor);
            }else {
                //更新
                CouponRepayMonitorVO monitor = monitors.get(0);
                monitor.setInterestWaitTotal(interestWaitFinal.get(i));
                monitor.setInterestYesTotal(interestReceived);
                monitor.setUpdateTime(GetDate.getNowTime10());
                monitor.setUpdateUser(CustomConstants.USERID_ADMIN);
                result = couponRepayMonitorClient.updateCouponRepayMonitor(monitor);
            }
        }

        logger.info("自动生成加息券每日收益统计数据结束");
        return result;
    }

    /**
     *
     * 统计当前三天代收收益列表
     * @author hsy
     * @param interestWaitListProcessed
     * @return
     */
    public List<BigDecimal> getInterestWaitListFinal(List<CouponRecoverCustomizeVO> interestWaitListProcessed){
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        BigDecimal interestWaitHoliday =  getInterestWaitHolidayTotal(interestWaitListProcessed);
        if(interestWaitHoliday.compareTo(BigDecimal.ZERO) == 0){
            for(int i=0; i<3; i++){
                result.add(new BigDecimal(interestWaitListProcessed.get(i).getRecoverInterest()));
            }
        }else {
            for(int i=0; i<3; i++){
                String recoverTime = (String)interestWaitListProcessed.get(i).getRecoverTime();
                Calendar recoverTimeC = Calendar.getInstance();
                recoverTimeC.setTime(GetDate.stringToDate3(recoverTime, DATE_FORMAT_2));
                if(this.checkIsHoliday(recoverTimeC)){
                    result.add(BigDecimal.ZERO);
                    continue;
                }else{
                    BigDecimal sumToday = new BigDecimal(interestWaitListProcessed.get(i).getRecoverInterest());
                    recoverTimeC.add(Calendar.DAY_OF_MONTH, 1);
                    if(this.checkIsHoliday(recoverTimeC)){
                        BigDecimal sumHoliday = this.getInterestWaitHoliday(interestWaitListProcessed, recoverTimeC);
                        sumToday = sumToday.add(sumHoliday);
                    }
                    result.add(sumToday);
                }
            }
        }

        return result;
    }

    /**
     *
     * 将不连续的待收收益列表处理为连续的收益列表
     * @author hsy
     * @param interestWaitList
     * @param calendarEnd
     * @return
     */
    public List<CouponRecoverCustomizeVO> processInterestWaitList(List<CouponRecoverCustomizeVO> interestWaitList, Calendar calendarEnd){
        List<CouponRecoverCustomizeVO> couponRecoverCustomizeVOS = new ArrayList<>();
        Calendar calendarNow = this.getStatisticStartTime();

        while(calendarNow.get(Calendar.DAY_OF_YEAR) < calendarEnd.get(Calendar.DAY_OF_YEAR)){
            String dateNowStr = GetDate.formatDate(calendarNow.getTime());
            boolean dataExist = false;
            for(CouponRecoverCustomizeVO interest : interestWaitList){
                if(interest.getRecoverTime().equals(dateNowStr)){
                    CouponRecoverCustomizeVO interestWait = new CouponRecoverCustomizeVO();
                    interestWait.setRecoverTime(dateNowStr);
                    interestWait.setRecoverInterest(interest.getRecoverInterest());
                    couponRecoverCustomizeVOS.add(interestWait);
                    dataExist = true;
                }
            }

            if(!dataExist){
                CouponRecoverCustomizeVO interestWait = new CouponRecoverCustomizeVO();
                interestWait.setRecoverTime(dateNowStr);
                interestWait.setRecoverInterest(BigDecimal.ZERO.toString());
                couponRecoverCustomizeVOS.add(interestWait);
            }

            calendarNow.add(Calendar.DAY_OF_MONTH, 1);
        }

        return couponRecoverCustomizeVOS;
    }

    /**
     *
     * 取得节假日代收收益总和
     * @param interestWaitListProcessed
     * @return
     */
    public BigDecimal getInterestWaitHolidayTotal(List<CouponRecoverCustomizeVO> interestWaitListProcessed){
        BigDecimal result = BigDecimal.ZERO;
        for(CouponRecoverCustomizeVO interestMap : interestWaitListProcessed){
            String recoverTime = (String)interestMap.getRecoverTime();
            Calendar recoverTimeC = Calendar.getInstance();
            recoverTimeC.setTime(GetDate.stringToDate3(recoverTime, DATE_FORMAT_2));
            if(this.checkIsHoliday(recoverTimeC)){
                result = result.add(new BigDecimal(interestMap.getRecoverInterest()));
            }
        }

        return result;
    }

    /**
     *
     * 获取紧邻当前时间的假期的待还总额
     * @param interestWaitListProcessed
     * @param calendar
     * @return
     */
    public BigDecimal getInterestWaitHoliday(List<CouponRecoverCustomizeVO> interestWaitListProcessed, Calendar calendar){
        BigDecimal result = BigDecimal.ZERO;
        int startIndex = 0;
        for(int i=0; i<interestWaitListProcessed.size(); i++){
            String dateStr = GetDate.formatDate(calendar.getTime());
            if(interestWaitListProcessed.get(i).getRecoverTime().equals(dateStr)){
                startIndex = i;
                break;
            }
        }

        for(int i=startIndex; i<interestWaitListProcessed.size(); i++){
            Calendar recoverTimeC = Calendar.getInstance();
            recoverTimeC.setTime(GetDate.stringToDate3((String)interestWaitListProcessed.get(i).getRecoverTime(), DATE_FORMAT_2));
            if(this.checkIsHoliday(recoverTimeC)){
                result = result.add(new BigDecimal(interestWaitListProcessed.get(i).getRecoverInterest()));
                recoverTimeC.add(Calendar.DAY_OF_MONTH, 1);
                if(!this.checkIsHoliday(recoverTimeC)){
                    break;
                }
            }
        }

        return result;
    }

    /**
     *
     * 获取节假日列表
     * @return
     */
    public List<HolidaysConfigVO> getHolidayList(){
//        HolidaysConfigExample example=new HolidaysConfigExample();
//        example.setOrderByClause("statr_time asc");
        String orderByClause = "statr_time asc";
        return holidaysConfigClient.selectHolidaysConfig(orderByClause);
//        List<HolidaysConfig> holidays = new ArrayList<HolidaysConfig>();
//        HolidaysConfig holiday = new HolidaysConfig();
//        holiday.setStatrTime("2016年07月26日");
//        holiday.setEndTime("2016年07月29日");
//        holiday.setEventsName("我的假期");
//        holidays.add(holiday);
//        return holidays;
    }

    /**
     *
     * 计算统计开始时间
     * @return
     */
    public Calendar getStatisticStartTime(){
        Calendar calendar = Calendar.getInstance();
//        calendar.set(2016, 6, 23);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     *
     * 递归计算统计结束时间
     * @param calendar
     */
    public void getStatisticEndTime(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String week = GetDate.getWeekOfDate(calendar.getTime());
        if(week.equals("周六") || week.equals("周日")){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            getStatisticEndTime(calendar);
        }

        List<HolidaysConfigVO> holidays = this.getHolidayList();
        for(HolidaysConfigVO holiday : holidays){
            Long hstart = GetDate.stringToDate3(holiday.getStatrTime(), DATE_FORMAT).getTime();
            Long hend = GetDate.stringToDate3(holiday.getEndTime(), DATE_FORMAT).getTime();
            if(calendar.getTime().getTime() >= hstart && calendar.getTime().getTime() <= hend){
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                getStatisticEndTime(calendar);
            }
        }

        return;
    }

    /**
     *
     * 当前日期是否是节假日判断
     * @author hsy
     * @param calendar
     * @return
     */
    public boolean checkIsHoliday(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String week = GetDate.getWeekOfDate(calendar.getTime());
        if(week.equals("周六") || week.equals("周日")){
            return true;
        }

        List<HolidaysConfigVO> holidays = this.getHolidayList();
        for(HolidaysConfigVO holiday : holidays){
            Long hstart = GetDate.stringToDate3(holiday.getStatrTime(), DATE_FORMAT).getTime();
            Long hend = GetDate.stringToDate3(holiday.getEndTime(), DATE_FORMAT).getTime();
            if(calendar.getTime().getTime() >= hstart && calendar.getTime().getTime() <= hend){
                return true;
            }
        }

        return false;
    }

}
