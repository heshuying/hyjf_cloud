package com.hyjf.data.trade.service.impl;

import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.JcUserAnalysis;
import com.hyjf.data.bean.jinchuang.JcUserInterest;
import com.hyjf.data.mongo.jinchuang.JcUserAnalysisDao;
import com.hyjf.data.mongo.jinchuang.JcUserInterestDao;
import com.hyjf.data.trade.service.DBService;
import com.hyjf.data.trade.entity.SexDistributedInfo;
import com.hyjf.data.trade.mapper.JcDataStatisticsCustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/28
 * @Description: 从数据库获取数据
 */
@Service
public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    @Resource
    private JcDataStatisticsCustomerMapper jcDataStatisticsCustomerMapper;

    @Autowired
    private JcUserInterestDao jcUserInterestDao;
    @Autowired
    private JcUserAnalysisDao jcUserAnalysisDao;


    /**
     * 更新当月用户收益（包扣当日）
     */
    @Override
    public void getUserInterestMonth() {

        String startDate = GetDate.getDateMyTimeInMillis(GetDate.getFirstDayOfMonth());
        String endDate = GetDate.formatDate();
        if(startDate.equals(endDate)){//如果当天为月初，则统计上个整月的数据
            startDate = GetDate.countDateYYYYMMDDHHMMSS(startDate,2,-1);
        }
        BigDecimal monthInterest = jcDataStatisticsCustomerMapper.getUserMonthInterest(startDate, endDate);

        JcUserInterest jcUserInterest = new JcUserInterest();
        jcUserInterest.setInterest(monthInterest);
        jcUserInterest.setTime(GetDate.formatTimeYYYYMM());
        jcUserInterest.setUpdateTime(GetDate.formatDate());
        JcUserInterest info = jcUserInterestDao.findMonthInterest();
        if(info != null){
            jcUserInterestDao.updateByParamInfoNotNull(info.getId(),jcUserInterest);
        }else{
            jcUserInterest.setCreateTime(GetDate.formatDate());
            jcUserInterestDao.save(jcUserInterest);
        }
        logger.info("----------金创更新每月用户收益完成！----------");
    }

    /**
     * 更新最近6个月的用户性别分布
     */
    @Override
    public void getSixMonthSexDistributed() {


        String startDate  = GetDate.formatDate(GetDate.getFirstDayOnMonth(GetDate.stringToDate2(GetDate.getCountDate(2,-5))));
        String endDate = GetDate.formatDate();
        List<SexDistributedInfo> list = jcDataStatisticsCustomerMapper.getSexDistributed(startDate, endDate);
        JcUserAnalysis jcUserAnalysis = new JcUserAnalysis();
        if(list != null && list.size() > 0){
            for (SexDistributedInfo info:list
                 ) {
                if("1".equals(info.getSex())){
                    jcUserAnalysis.setMaleCount(info.getCount());
                }else if("2".equals(info.getSex())){
                    jcUserAnalysis.setFemaleCount(info.getCount());
                }
            }
            jcUserAnalysis.setUpdateTime(GetDate.formatDate());
            JcUserAnalysis userAnalysis = jcUserAnalysisDao.getUserAnalysis();
            if(userAnalysis != null){
                jcUserAnalysisDao.updateByParamInfoNotNull(userAnalysis.getId(),jcUserAnalysis);
            }else{
                jcUserAnalysis.setUpdateTime(GetDate.formatDate());
                jcUserAnalysisDao.save(jcUserAnalysis);
            }
        }
        logger.info("----------金创更新最近6个月的用户性别分布完成！----------");
    }
}
