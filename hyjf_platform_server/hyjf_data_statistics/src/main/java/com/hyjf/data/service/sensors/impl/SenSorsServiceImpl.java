package com.hyjf.data.service.sensors.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.mongo.jinchuang.InterfaceConfigurationDao;
import com.hyjf.data.mongo.jinchuang.JcUserAnalysisDao;
import com.hyjf.data.mongo.jinchuang.JcUserConversionDao;
import com.hyjf.data.service.sensors.SenSorsService;
import com.hyjf.data.utils.constant.JcConstant;
import com.hyjf.data.vo.jinchuang.InterfaceConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            jcUserConversionDao.saveUserConversion(entity);
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
        }
        return url;
    }

    /**
     * 更新用户行为（近30天，包扣当天）
     */
    @Override
    public void updateMonthUserBehavior() {


    }

    /**
     * 更新当月交易规模（包含当天）
     */
    @Override
    public void updateMonthTransTotal() {

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
