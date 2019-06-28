package com.hyjf.data.service.sensors.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.*;
import com.hyjf.data.mongo.jinchuang.*;
import com.hyjf.data.service.sensors.SenSorsService;
import com.hyjf.data.utils.constant.JcConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/25
 * @Description: 获取更新神策数据
 */
@Service
public class SenSorsServiceImpl implements SenSorsService{

    private static final Logger logger = LoggerFactory.getLogger(SenSorsServiceImpl.class);

    @Autowired
    private InterfaceConfigurationDao interfaceConfigurationDao;

    @Autowired
    private JcUserConversionDao jcUserConversionDao;

    @Autowired
    private JcUserAnalysisDao jcUserAnalysisDao;

    @Autowired
    private JcUserPointDao jcUserPointDao;

    @Autowired
    private JcRegisterTradeDao jcRegisterTradeDao;

    @Autowired
    private JcDataStatisticsDao jcDataStatisticsDao;

    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

    @Value("${hyjf.sensors.token}")
    private String token;

    /**
     * 获取接口数据
     * @param parm
     * @param url
     * @return
     */
    @Override
    public Map getSenSorsData(Map parm, String url) {

        String jsonString = JSONUtils.toJSONString(parm);
        String post = HttpClientUtils.postJson(url, jsonString);
        Map map = (Map) JSONUtils.parse(post);
        return map;
    }


    /**
     * 更新当月用户转化（近30天，包扣当天）
     * @return
     */
    @Override
    public boolean updateMonthUserConvert() {
        Map param = new HashMap();
        String interfaceName = JcConstant.INTERFACENAME_SENSORS;
        String method = JcConstant.INTERFCE_SENSORS_METHOD_USERCONVERSION;
        String url = getSenSorsUrl(interfaceName,method);
        if(StringUtils.isBlank(url)){
            logger.error("---------神策未获取到相关路径！-----");
        }
        Date startDate = GetDate.getSomeDayBeforeOrAfter(new Date(), -30);
        String startDateStr = GetDate.formatDate(startDate);
        param.put("from_date",startDateStr);
        param.put("to_date",GetDate.formatDate(new Date()));
        param.put("extend_over_end_date",true);
        param.put("state","trends");
        param.put("funnel_id","77");
        param.put("use_cache",true);
        Map result = this.getSenSorsData(param, url);
        List<Map> funnelDetail = (List<Map>) result.get("funnel_detail");
        if(funnelDetail != null){
            Map map = funnelDetail.get(0);
            List<Map> step = (List<Map>) map.get("steps");
            JcUserConversion entity = new JcUserConversion();
            for (Map info:step
                 ) {
                if(info.get("event_name") != null && "sign_up".equals(info.get("event_name"))){
                    entity.setRegisterRate(info.get("conversion_rate").toString());
                }
                if(info.get("event_name") != null && "open_success".equals(info.get("event_name"))){
                    entity.setOpenAccountRate(info.get("conversion_rate").toString());
                }
                if(info.get("event_name") != null && "recharge_result".equals(info.get("event_name"))){
                    entity.setRechargeRate(info.get("conversion_rate").toString());
                }
                if(info.get("event_name") != null && "allTender".equals(info.get("event_name"))){
                    entity.setInvestRate(info.get("conversion_rate").toString());
                }
                if(info.get("event_name") != null && "investagain".equals(info.get("event_name"))){
                    entity.setReInvestRate(info.get("conversion_rate").toString());
                }
            }
            entity.setUpdateTime(GetDate.formatTime());
            JcUserConversion userConversion = jcUserConversionDao.getUserConversion();
            if(userConversion != null){//变更数据
                jcUserConversionDao.updateByParamInfoNotNull(userConversion.getId(),entity);
            }else{
                entity.setCreateTime(GetDate.formatTime());
                jcUserConversionDao.save(entity);
            }
            logger.info("----------金创更新神策数据：用户转化成功！--------");
        }
        return true;
    }


    private String getSenSorsUrl(String interfaceName, String method) {

        InterfaceConfiguration info = interfaceConfigurationDao.getInterfaceConfigurationByInterfceNmeAndMehtod(interfaceName, method);
        String url = null;
        if(info != null){
            url = info.getDomain();
            url = url + info.getUrl();
            url = url + "&token=" + token;
        }
        return url;
    }

    /**
     * 更新用户行为（近30天，包扣当天）
     */
    @Override
    public void updateMonthUserBehavior() {

        Map param = new HashMap();
        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS, JcConstant.INTERFCE_SENSORS_METHOD_USERBEHAVIOR);

        param.put("source_type","initial_event");
        Map sourceEventMap = new HashMap();
        sourceEventMap.put("event_name","sign_up");
        param.put("source_event",sourceEventMap);
        List<String> events = new ArrayList<>();
        events.add("receive_credit_assign");
        events.add("submit_credit_assign");
        events.add("open_success");
        events.add("withdraw_result");
        events.add("submit_tender");
        events.add("submit_intelligent_invest");
        events.add("sign_up");
        param.put("event_names",events);
        Date startDate = GetDate.getSomeDayBeforeOrAfter(new Date(), -30);
        String startDateStr = GetDate.formatDate(startDate);
        param.put("from_date",startDateStr);
        param.put("to_date",GetDate.formatDate(new Date()));
        param.put("session_interval",900);
        param.put("use_cache",true);
        Map senSorsData = this.getSenSorsData(param, url);
        // 优化格式，最终存储为前端需要的格式
        Map nodesMap = new HashMap();
        List<Map> nodeMap = new ArrayList<>();
        List<Map> linkMap = new ArrayList<>();
        List<List<Map>> nodesList = (List<List<Map>>) senSorsData.get("nodes");
        for (List<Map> node: nodesList
             ) {
            for (Map map:node
                 ) {
                Map info = new HashMap();
                info.put("id",map.get("id"));
                info.put("name",getEventsName((String) map.get("event_name")));
                nodeMap.add(info);
            }
        }

        List<List<Map>> linksList = (List<List<Map>>) senSorsData.get("links");
        for (List<Map> node: linksList
                ) {
            for (Map map:node
                    ) {
                if(map.get("is_wastage") != null && (boolean)map.get("is_wastage") == true){
                    continue;
                }
                Map info = new HashMap();
                info.put("source",map.get("source"));
                info.put("target",map.get("target"));
                info.put("value",map.get("times"));
                linkMap.add(info);
            }
        }
        nodesMap.put("nodes",nodeMap);
        nodesMap.put("links",linkMap);

        JcUserPoint info = new JcUserPoint();
        info.setJo(JSONUtils.toJSONString(nodesMap));
        info.setUpdateTime(GetDate.formatTime());
        JcUserPoint userPoint = jcUserPointDao.getUserPoint();
        if(userPoint != null){
            jcUserPointDao.updateByParamInfoNotNull(userPoint.getId(),info);
        }else{
            info.setCreateTime(GetDate.formatTime());
            jcUserPointDao.save(info);
        }
        logger.info("----------金创更新神策数据：用户行为 ，成功！--------");
    }


    /**
     * 获取事件描述
     * @param key
     * @return
     */
    private String getEventsName(String key){

        Map map =  new HashMap();
        map.put("receive_credit_assign","承接债转");
        map.put("submit_credit_assign","发起债转");
        map.put("open_success","开户成功");
        map.put("withdraw_result","提现成功");
        map.put("submit_tender","散标出借");
        map.put("submit_intelligent_invest","智投出借");
        map.put("sign_up","注册");
        return (String) map.get(key);
    }


    /**
     * 更新当月交易规模（包含当天）
     */
    @Override
    public void updateMonthTransTotal() {

        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("expression","sum(event.invest.tender_amount)+sum(event.receive_credit_assign.receive_credit_amount)|%d");
        List<String> events = new ArrayList<>();
        events.add("receive_credit_assign");
        events.add("invest");
        meMap.put("events",events);
        meMap.put("name","交易规模");
        meMap.put("format","%d");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);
        param.put("unit","month");
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcRegisterTrade info = new JcRegisterTrade();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        info.setDealSize(new BigDecimal(value.toString()));
        info.setUpdateTime(GetDate.formatTime());
        info.setTime(GetDate.formatTimeYYYYMM());

        JcRegisterTrade tradeInfo = jcRegisterTradeDao.getRegisterTradeByNowMonth();
        if(tradeInfo != null){
            jcRegisterTradeDao.updateByParamInfoNotNull(tradeInfo.getId(),info);
        }else{
            info.setCreateTime(GetDate.formatTime());
            jcRegisterTradeDao.save(info);
        }

        logger.info("-------------金创数据，当月交易规模更新完成！----");


    }

    /**
     * 更新当月注册用户数量（包含当天）
     */
    @Override
    public void updateMonthRegisterNumber() {

        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("event_name","sign_up");
        meMap.put("aggregator","unique");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);

        param.put("unit","month");
        Map filterMap = new HashMap();
        List<Map> conList = new ArrayList<>();
        Map conMap = new HashMap();
        conMap.put("field","event.sign_up.success");
        conMap.put("function","isTrue");
        conList.add(conMap);
        filterMap.put("conditions",conList);
        param.put("filter",filterMap);
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcRegisterTrade info = new JcRegisterTrade();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        info.setRegisterCount(value);
        info.setUpdateTime(GetDate.formatTime());
        JcRegisterTrade tradeInfo = jcRegisterTradeDao.getRegisterTradeByNowMonth();
        if(tradeInfo != null){
            jcRegisterTradeDao.updateByParamInfoNotNull(tradeInfo.getId(),info);
        }else{
            info.setTime(GetDate.formatTimeYYYYMM());
            info.setCreateTime(GetDate.formatTime());
            jcRegisterTradeDao.save(info);
        }

        logger.info("-------------金创数据，当月注册用户更新完成！----");


    }

    /**
     * 更新当月总出借笔数
     */
    @Override
    public void updateMonthTenderSumNumber() {
        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("event_name","allTender");
        meMap.put("aggregator","general");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);

        param.put("unit","month");
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcDataStatistics dinfo = new JcDataStatistics();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        dinfo.setInvestCount(value);
        dinfo.setUpdateTime(GetDate.formatTime());

        JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth();
        if(tradeInfo != null){
            jcDataStatisticsDao.updateByParamInfoNotNull(tradeInfo.getId(),dinfo);
        }else{
            dinfo.setTime(GetDate.formatTimeYYYYMM());
            dinfo.setCreateTime(GetDate.formatTime());
            jcDataStatisticsDao.save(dinfo);
        }

        logger.info("-------------金创数据，当月总出借笔数更新完成！----");

    }

    /**
     * 更新当月散标出借笔数
     */
    @Override
    public void updateMonthTenderNumber() {
        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("event_name","submit_tender");
        meMap.put("aggregator","general");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);

        param.put("unit","month");
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcDataStatistics dinfo = new JcDataStatistics();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        dinfo.setZtInvestCount(value);
        dinfo.setUpdateTime(GetDate.formatTime());

        JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth();
        if(tradeInfo != null){
            jcDataStatisticsDao.updateByParamInfoNotNull(tradeInfo.getId(),dinfo);
        }else{
            dinfo.setTime(GetDate.formatTimeYYYYMM());
            dinfo.setCreateTime(GetDate.formatTime());
            jcDataStatisticsDao.save(dinfo);
        }

        logger.info("-------------金创数据，当月散标出借笔数更新完成！----");

    }

    /**
     * 更新当月智投出借笔数
     */
    @Override
    public void updateMonthHjhTenderNumber() {

        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("event_name","submit_intelligent_invest");
        meMap.put("aggregator","general");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);

        param.put("unit","month");
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcDataStatistics dinfo = new JcDataStatistics();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        dinfo.setJhInvestCount(value);
        dinfo.setUpdateTime(GetDate.formatTime());

        JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth();
        if(tradeInfo != null){
            jcDataStatisticsDao.updateByParamInfoNotNull(tradeInfo.getId(),dinfo);
        }else{
            dinfo.setTime(GetDate.formatTimeYYYYMM());
            dinfo.setCreateTime(GetDate.formatTime());
            jcDataStatisticsDao.save(dinfo);
        }

        logger.info("-------------金创数据，当月智投出借笔数更新完成！----");
    }

    /**
     * 当月债转出借笔数
     */
    @Override
    public void updateMonthCreditTenderNumber() {
        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
        List<Map> measuresMap = new ArrayList<>();
        Map meMap = new HashMap();
        meMap.put("event_name","receive_credit_assign");
        meMap.put("aggregator","general");
        measuresMap.add(meMap);
        param.put("measures",measuresMap);

        param.put("unit","month");
        String fromDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);

        JcDataStatistics dinfo = new JcDataStatistics();

        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
        Integer value = values.get(0).get(0);
        dinfo.setCreditCount(value);
        dinfo.setUpdateTime(GetDate.formatTime());

        JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth();
        if(tradeInfo != null){
            jcDataStatisticsDao.updateByParamInfoNotNull(tradeInfo.getId(),dinfo);
        }else{
            dinfo.setTime(GetDate.formatTimeYYYYMM());
            dinfo.setCreateTime(GetDate.formatTime());
            jcDataStatisticsDao.save(dinfo);
        }

        logger.info("-------------金创数据，当月承接债转出借笔数更新完成！----");
    }



    /**
     * 更新近6个月的出借金额分布，前5个整月的数据，及当前月的所有数据
     */
    @Override
    public void updateSixMonthTenderAmountDistributed() {
        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_ADDICTIONS);
        Map meMap = new HashMap();
        meMap.put("event_name","invest");
        meMap.put("aggregator","SUM");
        meMap.put("field","event.$Anything.tender_amount");
        param.put("measure",meMap);

        param.put("unit","day");
        param.put("rollup_date",true);
        param.put("event_name","invest");
        param.put("measure_type","times");
        List<Integer> list = new ArrayList<>();
        list.add(5000);
        list.add(10000);
        list.add(50000);
        param.put("result_bucket_param",list);

        //获取当月的前5个月的第一天
        String fromDate = GetDate.formatDate(GetDate.getFirstDayOnMonth(GetDate.stringToDate2(GetDate.getCountDate(2,-5))));
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);


        JcUserAnalysis info = new JcUserAnalysis();
        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<Map> cellsList = (List<Map>) rows.get("cells");
        for (Map map:cellsList
             ) {
            if(map.get("bucket_end") != null && 5000 == (double)map.get("bucket_end")){
                info.setPrimaryInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") != null && 10000 == (double)map.get("bucket_end")){
                info.setMiddleInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") != null && 50000 == (double)map.get("bucket_end")){
                info.setSeniorInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") == null && 50000 == (double)map.get("bucket_start")){
                info.setHighestInvest(map.get("percent").toString());
            }
        }
        info.setUpdateTime(GetDate.formatTime());
        info.setTime(GetDate.formatTimeYYYYMM());

        JcUserAnalysis userAnalysis = jcUserAnalysisDao.getUserAnalysis();
        if(userAnalysis != null){
            jcUserAnalysisDao.updateByParamInfoNotNull(userAnalysis.getId(),info);
        }else{
            info.setCreateTime(GetDate.formatTime());
            jcUserAnalysisDao.save(info);
        }
        logger.info("-------------金创数据，最近6个月的出借金额分布更新完成！----");
    }

    /**
     * 更新最近6个月出借次数分布
     */
    @Override
    public void updateSixMonthTenderNumberDistributed() {
        Map param = new HashMap();

        String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_ADDICTIONS);


        param.put("unit","day");
        param.put("rollup_date",true);
        param.put("event_name","invest");
        param.put("measure_type","times");
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(11);
        param.put("result_bucket_param",list);

        String fromDate = GetDate.formatDate(GetDate.getFirstDayOnMonth(GetDate.stringToDate2(GetDate.getCountDate(2,-5))));
        param.put("from_date",fromDate);
        param.put("to_date",GetDate.formatDate());
        param.put("use_cache",true);
        Map senSorsData = getSenSorsData(param, url);


        JcUserAnalysis info = new JcUserAnalysis();
        List<Map> rowsList = (List<Map>) senSorsData.get("rows");
        Map rows = rowsList.get(0);
        List<Map> cellsList = (List<Map>) rows.get("cells");
        for (Map map:cellsList
                ) {
            if(map.get("bucket_end") != null && 2 == (double)map.get("bucket_end")){
                info.setOneInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") != null && 3 == (double)map.get("bucket_end")){
                info.setTwoInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") != null && 6 == (double)map.get("bucket_end")){
                info.setThreeInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") != null && 11 == (double)map.get("bucket_end")){
                info.setFourInvest(map.get("percent").toString());
            }
            if(map.get("bucket_end") == null && 11 == (double)map.get("bucket_start")){
                info.setFiveInvest(map.get("percent").toString());
            }
        }
        info.setUpdateTime(GetDate.formatTime());
        info.setTime(GetDate.formatTimeYYYYMM());

        JcUserAnalysis userAnalysis = jcUserAnalysisDao.getUserAnalysis();
        if(userAnalysis != null){
            jcUserAnalysisDao.updateByParamInfoNotNull(userAnalysis.getId(),info);
        }else{
            info.setCreateTime(GetDate.formatTime());
            jcUserAnalysisDao.save(info);
        }
        logger.info("-------------金创数据，最近6个月的出借次数分布更新完成！----");
    }

    /**
     * 获取实时总交易规模
     * @return
     */
    @Override
    public String getTotalInvestAmount() {

        DecimalFormat df = new DecimalFormat("#,##0");
        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
        if (entity != null) {
            df.format(entity.getTotalInvestAmount().setScale(0, BigDecimal.ROUND_DOWN));
        }
        return null;
    }
}
