package com.hyjf.am.user.controller.front.statistics;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.registrantchange.RegistrantChangeStatisticsService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.calculate.DateUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 注册人变化统计 - 上海大屏幕使用
 * @Author : huanghui
 */
@Api(value = "注册人变化统计 - 上海大屏幕使用")
@RestController
@RequestMapping("/am-user/batch")
public class RegistrantChangeStatisticsController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat month = new SimpleDateFormat("MM");

    private static DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:");

    @Autowired
    private RegistrantChangeStatisticsService registrantChangeStatisticsService;

    /** 今日新增注册人数  */
    private static final String TODAY_REG = "today_reg";

    /** 本月注册人数 */
    private static final String MONTH_REG = "month_reg";

    /** 总计注册人数 */
    private static final String TOTAL_REG_NUM = "total_reg_num";



    @RequestMapping("/registrantChangeStatistics")
    public void registrantChangeStatistics(){

        Date dNow = new Date();

        //当前日期 : 2018-07-01
        String nowDate = dateFormat.format(dNow);

        // 当前月 : 08
        String nowMonth = month.format(dNow);

        //今日注册人数
        String todayStartTimeStr = nowDate + " 00:00:00";
        String todayEndTimeStr = nowDate + " 23:59:59";

        Date todayStartTimeDate = null;
        Date todayEndTimeDate = null;
        try {
            todayStartTimeDate = formatDate.parse(todayStartTimeStr);
            todayEndTimeDate = formatDate.parse(todayEndTimeStr);
        }catch (ParseException e){
            e.printStackTrace();
        }

        Integer todayRegNum = this.registrantChangeStatisticsService.registrantChangeStatisticsCount(todayStartTimeDate, todayEndTimeDate);

        //本月注册人数
        //当前月起始时间 2018-08-01 00:00:00  ~ 2018-08-31 23:59:59
        String monthStartTimeStr = DateUtils.getMinMonthDate(nowDate) + " 00:00:00";
        String monthEndTimeStr = DateUtils.getMaxMonthDate(nowDate) + " 23:59:59";

        //转换String型日期为Date
        Date monthStartTimeDate = null;
        Date monthEndTimeDate = null;
        try{
            monthStartTimeDate = formatDate.parse(monthStartTimeStr);
            monthEndTimeDate = formatDate.parse(monthEndTimeStr);
        }catch (ParseException e){
            e.printStackTrace();
        }

        Integer monthRegNum = this.registrantChangeStatisticsService.registrantChangeStatisticsCount(monthStartTimeDate, monthEndTimeDate);

        //总计注册人数
        Date allStartTimeDate = null;
        Date allEndTimeDate = null;
        Integer allRegNum = this.registrantChangeStatisticsService.registrantChangeStatisticsCount(allStartTimeDate, allEndTimeDate);

        //今日新增充值人数, 今日新增投资人数
        Map<String, Object> regNumMap = new HashedMap();
        regNumMap.put(TODAY_REG, todayRegNum == null ? 0 : todayRegNum);
        regNumMap.put(MONTH_REG, monthRegNum == null ? 0 : monthRegNum);
        regNumMap.put(TOTAL_REG_NUM, allRegNum == null ? 0 : allRegNum);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("populationChange", regNumMap);

        //写入Redis
        //需和今日新增充值和投资人合并
        RedisUtils.set(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.REGISTRANT_STATISTICS + "2", jsonObject.toString(), 3600);
    }
}
