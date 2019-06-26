package com.hyjf.data.service.sensors.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.InterfaceConfiguration;
import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.bean.jinchuang.JcUserPoint;
import com.hyjf.data.mongo.jinchuang.*;
import com.hyjf.data.service.sensors.SenSorsService;
import com.hyjf.data.utils.constant.JcConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/25
 * @Description:
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

    /**
     * 获取接口数据
     * @param parm
     * @param url
     * @return
     */
    @Override
    public Map getSenSorsData(Map parm, String url) {

        String post = HttpClientUtils.post(url, parm);
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
            entity.setCreateTime(GetDate.formatTime());
            if(isExitsJcUserConversion()){//变更数据

            }else{
                jcUserConversionDao.save(entity);
            }
            logger.info("----------金创更新神策数据：用户转化成功！--------");
        }
        return true;
    }

    /**
     * 判断是否存在用户转化数据
     * @return
     */
    private boolean isExitsJcUserConversion() {


        JcUserConversion userConversion = jcUserConversionDao.getUserConversion();
        if(userConversion != null){
            return true;
        }
        return false;
    }

    private String getSenSorsUrl(String interfaceName, String method) {

        InterfaceConfiguration info = interfaceConfigurationDao.getInterfaceConfigurationByInterfceNmeAndMehtod(interfaceName, method);
        String url = null;
        if(info != null){
            url = info.getDomain();
            url = url + info.getUrl();
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
        info.setJo(nodesMap);
        info.setCreateTime(GetDate.formatTime());
        jcUserPointDao.save(info);
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

//    public static void main(String[] args) {
//        Integer fromDate = GetDate.getFirstDayOfMonth();
//        String time = GetDate.getDateMyTimeInMillis(fromDate);
//        System.out.println(time);
//    }

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
        //TODO 新建获取当月数据是否存在的逻辑，如果存在则变更
//        jcRegisterTradeDao
    }

    /**
     * 更新当月注册用户数量（包含当天）
     */
    @Override
    public void updateMonthRegisterNumber() {

    }

    @Override
    public void updateMonthTenderSumNumber() {

    }

    @Override
    public void updateMonthTenderNumber() {

    }

    @Override
    public void updateMonthHjhTenderNumber() {

    }

    @Override
    public void updateMonthCreditTenderNumber() {

    }

    @Override
    public void updateSixMonthTenderAmountDistributed() {

    }

    @Override
    public void updateSixMonthTenderNumberDistributed() {

    }
}
