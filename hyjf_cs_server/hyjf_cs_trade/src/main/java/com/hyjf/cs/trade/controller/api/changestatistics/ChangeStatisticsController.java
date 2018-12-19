package com.hyjf.cs.trade.controller.api.changestatistics;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 上海大屏幕资金变化统计接口
 * @Author : huanghui
 */
@Api(value = "api端-上海大屏幕接口",tags = "api端-上海大屏幕接口")
@RestController
@RequestMapping("/hyjf-api/server/fundsChanheStatistics")
public class ChangeStatisticsController extends BaseController {

    /** 今日新增注册人数  */
    private static final String TODAY_REG = "today_reg";

    /** 本月注册人数 */
    private static final String MONTH_REG = "month_reg";

    /** 总计注册人数 */
    private static final String TOTAL_REG_NUM = "total_reg_num";

    /** 今日新增出借人数 */
    private static final String TODAY_ACCOUNT_NUM = "today_account_num";

    /** 今日新增充值人数 */
    private static final String TODAY_RECHARGE_TIMES = "today_recharge_times";


    /**
     * 数据全部在Redis中, 所以写在一个控制器中.
     * 统计出借人
     * 统计出借
     * 统计充值
     * 注册人统计
     */
    @PostMapping("/reportForm.do")
    @ApiParam(required = true, name = "reportForm", value = "上海大屏幕统计接口")
    @ApiOperation(value = "上海大屏幕统计接口", httpMethod = "POST", notes = "上海大屏幕统计接口")
    public JSONObject reportForm(){
        JSONObject jsonObject = new JSONObject();

        // Redis 中入去注册人数统计
        String regNum1 = RedisUtils.get(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.REGISTRANT_STATISTICS + "1");
        String regNum2 = RedisUtils.get(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.REGISTRANT_STATISTICS + "2");

        logger.info("从Redis中获取注册人统计:" + regNum1);
        logger.info("从Redis中获取充值统计:" + regNum2);

        // 统计人数变化
        Map<String, Object> numberOfPeopleMap = new HashMap();
        if (StringUtils.isNotBlank(regNum1) && StringUtils.isNotBlank(regNum2)) {
            JSONObject populationChange01 = JSONObject.parseObject(regNum1);
            JSONObject populationChange02 = JSONObject.parseObject(regNum2);

            JSONObject populationChange1 = (JSONObject) populationChange01.get("populationChange");
            JSONObject populationChange2 = (JSONObject) populationChange02.get("populationChange");

            numberOfPeopleMap.put(TODAY_REG, populationChange2.get("today_reg"));
            numberOfPeopleMap.put(MONTH_REG, populationChange2.get("month_reg"));
            numberOfPeopleMap.put(TOTAL_REG_NUM, populationChange2.get("total_reg_num"));
            numberOfPeopleMap.put(TODAY_RECHARGE_TIMES, populationChange1.get("today_recharge_times"));
            numberOfPeopleMap.put(TODAY_ACCOUNT_NUM, populationChange1.get("today_account_num"));

        }

        // Redis 中读取 资金变化统计
        String fundChangeString = RedisUtils.get(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_RECHARGE);
        logger.info("从Redis中获取资金变化统计:" + fundChangeString);

        Map<String, Object> fundChangeMap = new HashMap<>();
        if (StringUtils.isNotBlank(fundChangeString)) {
            JSONObject fundChangeStringJson = JSONObject.parseObject(fundChangeString);
            fundChangeMap = (JSONObject) fundChangeStringJson.get("fundChange");
        }

        jsonObject.put("error", 0);
        jsonObject.put("statusDes", "请求成功");
        jsonObject.put("populationChange", numberOfPeopleMap);
        jsonObject.put("fundChange", fundChangeMap);
        return jsonObject;
    }

    @PostMapping("/investorStatistics.do")
    @ApiParam(required = true, name = "investorStatistics", value = "上海大屏幕出借人统计")
    @ApiOperation(value = "上海大屏幕出借人统计接口", httpMethod = "POST", notes = "上海大屏幕统出借人计接口")
    public JSONObject investorStatistics(){
        JSONObject jsonObject = new JSONObject();

        String statisticalInvestorString = RedisUtils.get(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_INVESTOR);

        logger.info("从Redis中获取出借人统计数据:" + statisticalInvestorString);

        Map<String, Object> statisticalInvestorMap = new HashMap<>();
        if (StringUtils.isNotBlank(statisticalInvestorString)) {
            JSONObject jsonString = JSONObject.parseObject(statisticalInvestorString);
            statisticalInvestorMap = (JSONObject) jsonString.get("investorStatistics");
        }

        jsonObject.put("error", 0);
        jsonObject.put("statusDes", "请求成功");
        jsonObject.put("investorStatistics", statisticalInvestorMap);
        return jsonObject;
    }

    @PostMapping("/fundStatistics.do")
    @ApiParam(required = true, name = "fundStatistics", value = "出借资金统计")
    @ApiOperation(value = "上海大屏幕出借资金统计接口", httpMethod = "POST", notes = "上海大屏幕出借资金统计接口")
    public JSONObject fundStatistics(){
        JSONObject jsonObject = new JSONObject();

        String investmentStatisticsString = RedisUtils.get(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.STATISTICAL_INVESTMENT);
        logger.info("从Redis中获取出借资金统计数据:" + investmentStatisticsString);
        Map<String, Object> investmentStatisticsMap = new HashMap<>();
        if (StringUtils.isNotBlank(investmentStatisticsString)) {
            JSONObject jsonString = JSONObject.parseObject(investmentStatisticsString);
            investmentStatisticsMap = (JSONObject) jsonString.get("investmentStatistics");
        }

        jsonObject.put("error", 0);
        jsonObject.put("statusDes", "请求成功");
        jsonObject.put("investorStatistics", investmentStatisticsMap);

        return jsonObject;
    }
}
