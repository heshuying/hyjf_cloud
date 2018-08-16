package com.hyjf.am.trade.controller.batch;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.FundChangeStatisticsService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资金变化统计 定时任务
 * @Author : huanghui
 */
@ApiIgnore
@RestController
@RequestMapping("/am-trade/batch")
public class FundChangeStatisticsController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat month = new SimpleDateFormat("MM");

    /** 今日新增注册人数  */
    private static final String TODAY_REG = "today_reg";

    /** 今日新增投资人数 */
    private static final String TODAY_ACCOUNT_NUM = "today_account_num";

    /** 今日新增充值人数 */
    private static final String TODAY_RECHARGE_TIMES = "today_recharge_times";

    /** 本月注册人数 */
    private static final String MONTH_REG = "month_reg";

    /** 总计注册人数 */
    private static final String TOTAL_REG_NUM = "total_reg_num";

    /** 今日投资人数 */
    private static final String TODAY_NUMBER = "todayNumber";

    /** 本周投资人数 */
    private static final String WEEK_NUMBER = "weekNumber";

    /** 本月投资人数 */
    private static final String MONTH_NUMBER = "monthNumber";

    /** 本季度投资人数 */
    private static final String QUARTER_NUMBER = "quarterNumber";

    /** 本年度投资人数 */
    private static final String YEAR_NUMBER = "yearNumber";

    /** 上线以来所有投资人数 */
    private static final String ALL_NUMBER = "allNumber";



    /** 今日充值金额 */
    private static final String TODAY_RECHARGE_MONEY = "today_recharge_money";

    /** 昨日充值金额 */
    private static final String YESTERDAY_RECHARGE_MONEY = "yesterday_recharge_money";

    /** 本月充值金额 */
    private static final String MONTH_RECHARGE_MONEY = "month_recharge_money";

    /** 今日投资金额 */
    private static final String TODAY_ACCOUNT_MONEY = "today_account_money";

    /** 昨日投资金额 */
    private static final String YESTERDAY_ACCOUNT_MONEY = "yesterday_account_money";

    /** 本月投资金额 */
    private static final String MONTH_ACCOUNT_MONEY = "month_account_money";

    /** 今日投资金额 (散标+债权+汇天利) */
    private static final String TODAY_STATISTICS = "todayStatistics";

    /** 本周投资金额 (散标+债权+汇天利) */
    private static final String WEEK_STATISTICS = "weekStatistics";

    /** 本月投资金额 (散标+债权+汇天利) */
    private static final String MONTH_STATISTICS = "monthStatistics";

    /** 本季度投资金额 (散标+债权+汇天利) */
    private static final String QUARTER_STATISTICS = "quarterStatistics";

    /** 年度投资金额 (散标+债权+汇天利) */
    private static final String YEAR_STATISTICS = "yearStatistics";

    /** 上线以来所有投资金额 */
    private static final String ALL_STATISTICS = "allStatistics";

    @Autowired
    private FundChangeStatisticsService fundChangeStatisticsService;

    @RequestMapping("/countRechargeMoney")
    public void countRechargeMoney(){



        Date dNow = new Date();

        //当前日期 : 2018-07-01
        String nowDate = dateFormat.format(dNow);

        //昨天日期
        String yesterdayDate = dateFormat.format(new Date(dNow.getTime() - (long)1 * 24 * 60 * 60 * 1000));

        // 当前月 : 08
        String nowMonth = month.format(dNow);

        Map<String, Object> params = new HashedMap();

        //今日充值金额
        params.put("startTime", nowDate);
        params.put("endTime", nowDate);
        Integer todayRechargeMoney = this.fundChangeStatisticsService.countRechargeMoney(params);
        //今日投资金额(直投 债权 计划)
        Integer todayAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);
        Integer todayCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentCreditTenderMoney(params);
        Integer todayHjhCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentHjhCreditTenderMoney(params);
        //今日投资人数
        Integer todayAccountInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params);

        //昨日充值金额
        params.put("startTime", yesterdayDate);
        params.put("endTime", yesterdayDate);
        Integer yesterdayRechargeMoney = this.fundChangeStatisticsService.countRechargeMoney(params);
        //昨日投资金额(直投 债权 计划)
        Integer yesterdayAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);


        //本周投资金额
        params.put("startTime", DateUtils.getCurrentMonday());
        params.put("endTime", DateUtils.getPreviousSunday());
        Integer currentAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);
        Integer currentCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentCreditTenderMoney(params);
        Integer currentHjhCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentHjhCreditTenderMoney(params);
        // 本周投资人数
        Integer currentAccountInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params);

        //本月充值金额
        params.put("startTime", DateUtils.getMinMonthDate(nowDate));
        params.put("endTime",  DateUtils.getMaxMonthDate(nowDate));
        Integer monthRechargeMoney = this.fundChangeStatisticsService.countRechargeMoney(params);

        //本月投资金额(直投 债权 计划)
        Integer monthAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);
        Integer monthCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentCreditTenderMoney(params);
        Integer monthHjhCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentHjhCreditTenderMoney(params);
        //本月投资人数
        Integer monthAccountInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params);

        //本季度投资金额(直投 债权 计划)
        params.put("startTime", DateUtils.getCurrentQuarterStartTime(nowMonth));
        params.put("endTime",  DateUtils.getCurrentQuarterEndTime(nowMonth));
        Integer quarterAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);
        Integer quarterCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentCreditTenderMoney(params);
        Integer quarterHjhCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentHjhCreditTenderMoney(params);
        //本季度投资人数
        Integer quarterAccountInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params);

        //年度投资金额(直投 债权 计划)
        params.put("startTime", DateUtils.getMinYearDate(nowDate));
        params.put("endTime",  DateUtils.getMaxYearDate(nowDate));
        Integer yearAccountMoney = this.fundChangeStatisticsService.countInvestmentMoney(params);
        Integer yearCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentCreditTenderMoney(params);
        Integer yearHjhCreditAccountMoney = this.fundChangeStatisticsService.countInvestmentHjhCreditTenderMoney(params);
        //本年度投资人数
        Integer yearAccountInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params);

        //上线以来所有投资金额
        Integer countAllStatistics = this.fundChangeStatisticsService.countAllAccount();
        //上线以来所有投资人数

        Map<String, Object> params1 = new HashedMap();
        Integer countAllInvestmentPeople = this.fundChangeStatisticsService.getNumberOfInvestors(params1);


        //投资资金统计Map
        Map<String, Object> investmentFundStatisticsMap = new HashedMap();
        investmentFundStatisticsMap.put(TODAY_STATISTICS, ((todayAccountMoney == null ? 0 : todayAccountMoney) + (todayCreditAccountMoney == null ? 0 : todayCreditAccountMoney) + (todayHjhCreditAccountMoney == null ? 0 : todayHjhCreditAccountMoney)) / 10000);
        investmentFundStatisticsMap.put(WEEK_STATISTICS, ((currentAccountMoney == null ? 0 : currentAccountMoney) + (currentCreditAccountMoney == null ? 0 : currentCreditAccountMoney) + (currentHjhCreditAccountMoney == null ? 0 : currentHjhCreditAccountMoney)) / 10000);
        investmentFundStatisticsMap.put(MONTH_STATISTICS, ((monthAccountMoney == null ? 0 : monthAccountMoney) + (monthCreditAccountMoney == null ? 0 : monthCreditAccountMoney) + (monthHjhCreditAccountMoney == null ? 0 : monthHjhCreditAccountMoney)) / 10000);
        investmentFundStatisticsMap.put(QUARTER_STATISTICS, ((quarterAccountMoney == null ? 0 : quarterAccountMoney) + (quarterCreditAccountMoney == null ? 0 : quarterCreditAccountMoney) + (quarterHjhCreditAccountMoney == null ? 0 : quarterHjhCreditAccountMoney)) / 10000);
        investmentFundStatisticsMap.put(YEAR_STATISTICS, ((yearAccountMoney == null ? 0 : yearAccountMoney) + (yearCreditAccountMoney == null ? 0 : yearCreditAccountMoney) + (yearHjhCreditAccountMoney == null ? 0 : yearHjhCreditAccountMoney)) / 10000);
        investmentFundStatisticsMap.put(ALL_STATISTICS, (countAllStatistics == null ? 0 : countAllStatistics) / 10000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("investmentStatistics", investmentFundStatisticsMap);

        //写入Redis
        RedisUtils.set(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_INVESTMENT, jsonObject.toString(), 3600);

        //充值投资统计
        Map<String, Object> fundChangeMap = new HashedMap();
        fundChangeMap.put(TODAY_RECHARGE_MONEY, (todayRechargeMoney == null ? 0 : todayRechargeMoney) / 10000);
        fundChangeMap.put(YESTERDAY_RECHARGE_MONEY, (yesterdayRechargeMoney == null ? 0 : yesterdayRechargeMoney) / 10000);
        fundChangeMap.put(TODAY_ACCOUNT_MONEY, (todayAccountMoney == null ? 0 : todayAccountMoney) / 10000);
        fundChangeMap.put(YESTERDAY_ACCOUNT_MONEY, (yesterdayAccountMoney == null ? 0 : yesterdayAccountMoney) / 10000);
        fundChangeMap.put(MONTH_RECHARGE_MONEY, (monthRechargeMoney == null ? 0 : monthRechargeMoney) / 10000);
        fundChangeMap.put(MONTH_ACCOUNT_MONEY, (monthAccountMoney == null ? 0 : monthAccountMoney) / 10000);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("fundChange", fundChangeMap);

        //写入Redis
        RedisUtils.set(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_RECHARGE, jsonObject1.toString(), 3600);

        //投资人统计
        Map<String, Object> investorStatisticsMap = new HashedMap();
        investorStatisticsMap.put(TODAY_NUMBER, todayAccountInvestmentPeople == null ? 0 : todayAccountInvestmentPeople);
        investorStatisticsMap.put(WEEK_NUMBER, currentAccountInvestmentPeople == null ? 0 : currentAccountInvestmentPeople);
        investorStatisticsMap.put(MONTH_NUMBER, monthAccountInvestmentPeople == null ? 0 : monthAccountInvestmentPeople);
        investorStatisticsMap.put(QUARTER_NUMBER, quarterAccountInvestmentPeople == null ? 0 : quarterAccountInvestmentPeople);
        investorStatisticsMap.put(YEAR_NUMBER, yearAccountInvestmentPeople == null ? 0 : yearAccountInvestmentPeople);
        investorStatisticsMap.put(ALL_NUMBER, countAllInvestmentPeople == null ? 0 : countAllInvestmentPeople);

        JSONObject jsonObjectPople = new JSONObject();
        jsonObjectPople.put("investorStatistics", investorStatisticsMap);

        //写入Redis
        RedisUtils.set(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_INVESTOR, jsonObject1.toString(), 3600);
    }
}
