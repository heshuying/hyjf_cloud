package com.hyjf.data.sensors.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.*;
import com.hyjf.data.mongo.jinchuang.*;
import com.hyjf.data.sensors.SenSorsService;
import com.hyjf.data.utils.constant.JcConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/25
 * @Description: 获取更新神策数据
 */
@Service
public class SenSorsServiceImpl implements SenSorsService {

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

    @Value("${hyjf.sensors.token}")
    private String token;

    @Value("${hyjf.sensors.project}")
    private String sensorsProject;

    //交易规模
    private static String JCREGISTERTRADEFLAG_TRADE = "dealSize";
    //注册人数
    private static String JCREGISTERTRADEFLAG_REGIS = "regis";
    //总出借笔数
    private static String JCDATASTATSIC_TOTALNUM= "investCount";
    //散标出借笔数
    private static String JCDATASTATSIC_TENDERNUM= "ztInvestCount";
    //智投出借笔数
    private static String JCDATASTATSIC_JHNUMBER= "jhInvestCount";
    //承接债转笔数
    private static String JCDATASTATSIC_CREDITNUM= "creditCount";

    /**
     * 获取接口数据
     * @param parm
     * @param url
     * @return
     */
    @Override
    public Map getSenSorsData(Map parm, String url) {

        logger.info("---------请求神策参数：" + parm);
        logger.info("---------请求url：" + url);
        String jsonString = JSONUtils.toJSONString(parm);
        Map header = new HashMap();
        header.put("sensorsdata-project",sensorsProject);
        String post = HttpClientUtils.postJson(url, jsonString,header);
        Map map = (Map) JSONUtils.parse(post);
        return map;
    }


    /**
     * 更新当月用户转化（近30天，包扣当天）
     * @return
     */
    @Override
    public boolean updateMonthUserConvert() {
        try{
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
            param.put("funnel_id",getfunnelseId(url));
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
                        entity.setRegisterRate(info.get("converted_user").toString());
                    }
                    if(info.get("event_name") != null && "open_success".equals(info.get("event_name"))){
                        entity.setOpenAccountRate(info.get("converted_user").toString());
                    }
                    if(info.get("event_name") != null && "recharge_result".equals(info.get("event_name"))){
                        entity.setRechargeRate(info.get("converted_user").toString());
                    }
                    if(info.get("event_name") != null && "invest".equals(info.get("event_name"))){
                        entity.setInvestRate(info.get("converted_user").toString());
                    }
                    if(info.get("event_name") != null && "investagain".equals(info.get("event_name"))){
                        entity.setReInvestRate(info.get("converted_user").toString());
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
        }catch (Exception e){
            e.printStackTrace();
            logger.info("----------金创更新神策数据：用户转化异常！--------");
            return false;
        }

    }

    private Object getfunnelseId(String url) {
        String[] split = url.split("/");
        return split[6];
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

        try {

            Map param = new HashMap();
            String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS, JcConstant.INTERFCE_SENSORS_METHOD_USERBEHAVIOR);

            param.put("source_type","initial_event");
            Map sourceEventMap = new HashMap();
            sourceEventMap.put("event_name","sign_up");
            param.put("source_event",sourceEventMap);
            List<String> events = new ArrayList<>();
            events.add("open_success");
            events.add("receive_credit_assign");
            events.add("recharge_result");
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
            if(nodesList != null && nodesList.size() > 0){

                int size = nodesList.size();
                if(size > 4){
                    size = 4;
                }
                for (int i = 0; i < size; i++) {
                    List<Map> node = nodesList.get(i);
                    for (Map map:node
                            ) {
                        Map info = new HashMap();
                        info.put("id",map.get("id"));
                        info.put("name",getEventsName((String) map.get("event_name")));
                        nodeMap.add(info);
                    }
                }

                List<List<Map>> linksList = (List<List<Map>>) senSorsData.get("links");
                for (int i = 0; i < size-1; i++) {
                    List<Map> node = linksList.get(i);
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
            }else{
                logger.info("----------金创更新神策数据：用户行为 ，无返回数据！--------");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("------------金创更新神策数据：用户行为 ，异常");
        }
    }


    /**
     * 获取事件描述
     * @param key
     * @return
     */
    private String getEventsName(String key){

        Map map =  new HashMap();
        map.put("receive_credit_assign","承接债转");
        map.put("recharge_result","充值成功");
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


        try {

            Map param = new HashMap();

            String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
            List<Map> measuresMap = new ArrayList<>();
            Map meMap = new HashMap();
            meMap.put("expression","sum(event.invest.tender_amount)+sum(event.receive_credit_assign.receive_credit_amount)|%2f");
            List<String> events = new ArrayList<>();
            events.add("receive_credit_assign");
            events.add("invest");
            meMap.put("events",events);
            meMap.put("name","交易规模");
            meMap.put("format","%2f");
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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Double>> values = (List<List<Double>>) rows.get("values");
                double value = values.get(0).get(0);
                info.setDealSize(new BigDecimal(String.valueOf(value)));
                info.setUpdateTime(GetDate.formatTime());

                //查询当月数据
                JcRegisterTrade tradeInfo = jcRegisterTradeDao.getRegisterTradeByNowMonth(GetDate.formatTimeYYYYMM());
                //更新数据
                updateJcRegisterTrade(tradeInfo,info,fromDate,param,url,JCREGISTERTRADEFLAG_TRADE);
                logger.info("-------------金创数据，当月交易规模更新完成！----");
            }else{
                logger.info("-------------金创数据，当月交易规模无返回数据！----");

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("-------------金创数据，当月交易规模异常！----");
        }


    }


    private String getYYYYMMDateStr(String newFromDate) {
        String[] split = newFromDate.split("-");
        String result = split[0]+split[1];
        return result;
    }



    /**
     * 更新当月注册用户数量（包含当天）
     */
    @Override
    public void updateMonthRegisterNumber() {

        try {

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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
                Integer value = values.get(0).get(0);
                info.setRegisterCount(value);
                info.setUpdateTime(GetDate.formatTime());
                JcRegisterTrade tradeInfo = jcRegisterTradeDao.getRegisterTradeByNowMonth(GetDate.formatTimeYYYYMM());
                //更新数据
                updateJcRegisterTrade(tradeInfo,info,fromDate,param,url,JCREGISTERTRADEFLAG_REGIS);
                logger.info("-------------金创数据，当月注册用户更新完成！----");
            }else{
                logger.info("-------------金创数据，当月注册用户更新无返回数据！----");

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("-------------金创数据，当月注册用户更新异常！----");
        }


    }



    private void updateJcRegisterTrade(JcRegisterTrade tradeInfo,JcRegisterTrade info,String fromDate,Map param,String url,String flag){
        if(tradeInfo != null){
            jcRegisterTradeDao.updateByParamInfoNotNull(tradeInfo.getId(),info);
        }else{
            info.setTime(GetDate.formatTimeYYYYMM());
            info.setCreateTime(GetDate.formatTime());
            if(JCREGISTERTRADEFLAG_TRADE.equals(flag)){
                info.setDealStatus(0);
            }else if(JCREGISTERTRADEFLAG_REGIS.equals(flag)){
                info.setRegisterStatus(0);
            }
            jcRegisterTradeDao.save(info);
        }
        //由于更新时间为 当日 23：50 月底最后一天这个时间之后的数据会统计不到，每月第一天统计一次上个整月的数据
        if(fromDate.equals(GetDate.formatDate())){
            Integer integer = GetDate.countDate(GetDate.getDayStart10(fromDate), 2, -1);
            String newFromDate = GetDate.formatDate(GetDate.getDate(integer));
            param.put("from_date",newFromDate);
            String newtoDate = GetDate.formatDate(GetDate.getDate(GetDate.countDate(GetDate.getDayStart10(fromDate),5,-1)));
            param.put("to_date", newtoDate);
            String dateStr = getYYYYMMDateStr(newFromDate);
            //查询上月数据
            JcRegisterTrade lasttradeInfo = jcRegisterTradeDao.getRegisterTradeByNowMonth(dateStr);
            if(lasttradeInfo != null){

                int endStatus = 0;
                if(JCREGISTERTRADEFLAG_TRADE.equals(flag)){
                    endStatus = lasttradeInfo.getDealStatus();
                }else if(JCREGISTERTRADEFLAG_REGIS.equals(flag)){
                    endStatus = lasttradeInfo.getRegisterStatus();
                }
                if(0 == endStatus){//上月数据未更新
                    Map lastsenSorsData = getSenSorsData(param, url);
                    JcRegisterTrade lastinfo = new JcRegisterTrade();

                    List<Map> lastrowsList = (List<Map>) lastsenSorsData.get("rows");
                    if(lastrowsList != null && lastrowsList.size() > 0){

                        Map lastrows = lastrowsList.get(0);
                        List<List<Integer>> lastvalues = (List<List<Integer>>) lastrows.get("values");
                        Integer lastvalue = lastvalues.get(0).get(0);
                        if(this.JCREGISTERTRADEFLAG_TRADE.equals(flag)){
                            lastinfo.setDealSize(new BigDecimal(lastvalue.toString()));
                            lastinfo.setDealStatus(1);
                        }else if(this.JCREGISTERTRADEFLAG_REGIS.equals(flag)){
                            lastinfo.setRegisterCount(lastvalue);
                            lastinfo.setRegisterStatus(1);
                        }

                        lastinfo.setUpdateTime(GetDate.formatTime());
                        jcRegisterTradeDao.updateByParamInfoNotNull(lasttradeInfo.getId(),lastinfo);
                    }
                }
            }

        }
    }

    /**
     * 更新当月总出借笔数
     */
    @Override
    public void updateMonthTenderSumNumber() {
        try {

            Map param = new HashMap();

            String url = getSenSorsUrl(JcConstant.INTERFACENAME_SENSORS,JcConstant.INTERFCE_SENSORS_METHOD_EVENTS);
            List<Map> measuresMap = new ArrayList<>();
            Map meMap = new HashMap();
            meMap.put("event_name","investall");
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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
                Integer value = values.get(0).get(0);
                dinfo.setInvestCount(value);
                dinfo.setUpdateTime(GetDate.formatTime());

                JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth(GetDate.formatTimeYYYYMM());
                //更新数据
                updateJcDataStatis(tradeInfo,dinfo,fromDate,param,url,JCDATASTATSIC_TOTALNUM);

                logger.info("-------------金创数据，当月总出借笔数更新完成！----");
            }else{
                logger.info("-------------金创数据，当月总出借笔数更新无返回数据！----");
            }
        }catch (Exception e){
            logger.info("-------------金创数据，当月总出借笔数更新error！----");
        }

    }

    private void updateJcDataStatis(JcDataStatistics tradeInfo,JcDataStatistics dinfo,String fromDate,Map param,String url,String flag){
        if(tradeInfo != null){
            jcDataStatisticsDao.updateByParamInfoNotNull(tradeInfo.getId(),dinfo);
        }else{
            dinfo.setTime(GetDate.formatTimeYYYYMM());
            dinfo.setCreateTime(GetDate.formatTime());
            if(JCDATASTATSIC_TOTALNUM.equals(flag)){
                dinfo.setInvestStatus(0);
            }else if(JCDATASTATSIC_TENDERNUM.equals(flag)){
                dinfo.setZtInvestStatus(0);
            }else if(JCDATASTATSIC_JHNUMBER.equals(flag)){
                dinfo.setJhInvestStatus(0);
            }else if(JCDATASTATSIC_CREDITNUM.equals(flag)){
                dinfo.setCreditStatus(0);
            }
            jcDataStatisticsDao.save(dinfo);
        }
        //由于更新时间为 当日 23：50 月底最后一天这个时间之后的数据会统计不到，每月第一天统计一次上个整月的数据
        if(fromDate.equals(GetDate.formatDate())){
            Integer integer = GetDate.countDate(GetDate.getDayStart10(fromDate), 2, -1);
            String newFromDate = GetDate.formatDate(GetDate.getDate(integer));
            param.put("from_date",newFromDate);
            String newtoDate = GetDate.formatDate(GetDate.getDate(GetDate.countDate(GetDate.getDayStart10(fromDate),5,-1)));
            param.put("to_date", newtoDate);
            String dateStr = getYYYYMMDateStr(newFromDate);
            //查询上月数据
            JcDataStatistics lasttradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth(dateStr);
            if(lasttradeInfo != null){
                int endStatus = 0;
                if(JCDATASTATSIC_TOTALNUM.equals(flag)){
                    endStatus = lasttradeInfo.getInvestStatus();
                }else if(JCDATASTATSIC_TENDERNUM.equals(flag)){
                    endStatus = lasttradeInfo.getZtInvestStatus();
                }else if(JCDATASTATSIC_JHNUMBER.equals(flag)){
                    endStatus = lasttradeInfo.getJhInvestStatus();
                }else if(JCDATASTATSIC_CREDITNUM.equals(flag)){
                    endStatus = lasttradeInfo.getCreditStatus();
                }
                if(0 == endStatus){//上月数据未更新

                    Map lastsenSorsData = getSenSorsData(param, url);

                    JcDataStatistics ldinfo = new JcDataStatistics();
                    List<Map> lrowsList = (List<Map>) lastsenSorsData.get("rows");
                    if(lrowsList != null && lrowsList.size()  > 0){

                        Map lrows = lrowsList.get(0);
                        List<List<Integer>> lvalues = (List<List<Integer>>) lrows.get("values");
                        Integer lvalue = lvalues.get(0).get(0);
                        if(JCDATASTATSIC_TOTALNUM.equals(flag)){
                            ldinfo.setInvestCount(lvalue);
                            ldinfo.setInvestStatus(1);
                        }else if(JCDATASTATSIC_TENDERNUM.equals(flag)){
                            ldinfo.setZtInvestCount(lvalue);
                            ldinfo.setZtInvestStatus(1);
                        }else if(JCDATASTATSIC_JHNUMBER.equals(flag)){
                            ldinfo.setJhInvestCount(lvalue);
                            ldinfo.setJhInvestStatus(1);
                        }else if(JCDATASTATSIC_CREDITNUM.equals(flag)){
                            ldinfo.setCreditStatus(1);
                            ldinfo.setCreditCount(lvalue);
                        }
                        ldinfo.setUpdateTime(GetDate.formatTime());
                        jcDataStatisticsDao.updateByParamInfoNotNull(lasttradeInfo.getId(),ldinfo);
                    }
                }
            }

        }
    }

    /**
     * 更新当月散标出借笔数
     */
    @Override
    public void updateMonthTenderNumber() {
        try {

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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
                Integer value = values.get(0).get(0);
                dinfo.setZtInvestCount(value);
                dinfo.setUpdateTime(GetDate.formatTime());

                JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth(GetDate.formatTimeYYYYMM());
                updateJcDataStatis(tradeInfo,dinfo,fromDate,param,url,JCDATASTATSIC_TENDERNUM);

                logger.info("-------------金创数据，当月散标出借笔数更新完成！----");
            }else{
                logger.info("-------------金创数据，当月散标出借笔数更新无返回数据！----");

            }
        }catch (Exception e){
            logger.info("-------------金创数据，当月散标出借笔数更新error！----");
            e.printStackTrace();
        }

    }

    /**
     * 更新当月智投出借笔数
     */
    @Override
    public void updateMonthHjhTenderNumber() {

        try {

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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
                Integer value = values.get(0).get(0);
                dinfo.setJhInvestCount(value);
                dinfo.setUpdateTime(GetDate.formatTime());

                JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth(GetDate.formatTimeYYYYMM());
                updateJcDataStatis(tradeInfo,dinfo,fromDate,param,url,JCDATASTATSIC_JHNUMBER);

                logger.info("-------------金创数据，当月智投出借笔数更新完成！----");
            }else{
                logger.info("-------------金创数据，当月智投出借笔数更新无返回数据！----");
            }
        }catch (Exception e){
            logger.info("-------------金创数据，当月智投出借笔数更新error！----");
            e.printStackTrace();
        }
    }

    /**
     * 当月债转出借笔数
     */
    @Override
    public void updateMonthCreditTenderNumber() {
        try {

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
            if(rowsList != null && rowsList.size() > 0){

                Map rows = rowsList.get(0);
                List<List<Integer>> values = (List<List<Integer>>) rows.get("values");
                Integer value = values.get(0).get(0);
                dinfo.setCreditCount(value);
                dinfo.setUpdateTime(GetDate.formatTime());

                JcDataStatistics tradeInfo = jcDataStatisticsDao.getDataStatisticsByNowMonth(GetDate.formatTimeYYYYMM());
                updateJcDataStatis(tradeInfo,dinfo,fromDate,param,url,JCDATASTATSIC_CREDITNUM);

                logger.info("-------------金创数据，当月承接债转出借笔数更新完成！----");
            }else{
                logger.info("-------------金创数据，当月承接债转出借笔数更新无数据返回！----");

            }
        }catch (Exception e){
            logger.info("-------------金创数据，当月承接债转出借笔数更新error！----");
            e.printStackTrace();
        }
    }



    /**
     * 更新近6个月的出借金额分布，前5个整月的数据，及当前月的所有数据
     */
    @Override
    public void updateSixMonthTenderAmountDistributed() {
        try {

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
            if(rowsList != null && rowsList.size() > 0){

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
            }else{
                logger.info("-------------金创数据，最近6个月的出借金额分布更新无返回数据！----");

            }
        }catch (Exception e){
            logger.info("-------------金创数据，最近6个月的出借金额分布更新error！----");
            e.printStackTrace();
        }
    }

    /**
     * 更新最近6个月出借次数分布
     */
    @Override
    public void updateSixMonthTenderNumberDistributed() {

        try {

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
            if(rowsList != null && rowsList.size() > 0){

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
            }else{
                logger.info("-------------金创数据，最近6个月的出借次数分布更新无数据返回！----");

            }
        }catch (Exception e){
            logger.info("-------------金创数据，最近6个月的出借次数分布更新error！----");
            e.printStackTrace();
        }
    }

}
