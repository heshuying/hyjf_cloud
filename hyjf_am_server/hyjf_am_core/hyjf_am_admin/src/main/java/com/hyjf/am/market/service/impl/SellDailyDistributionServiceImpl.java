package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.SellDailyDistributionMapper;
import com.hyjf.am.market.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.market.dao.model.auto.SellDailyDistributionExample;
import com.hyjf.am.market.service.SellDailyDistributionService;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version SellDailyDistributionServiceImpl, v0.1 2018/10/8 11:37
 */
@Service
public class SellDailyDistributionServiceImpl implements SellDailyDistributionService {

    private static final Logger logger = LoggerFactory.getLogger(SellDailyDistributionServiceImpl.class);
    @Autowired
    SellDailyDistributionMapper sellDailyDistributionMapper;
    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Integer queryTotal(EmailRecipientRequest form) {
        String businessName = form.getBusinessName();
        Integer status = form.getStatus();
        String timeStartCreateSrch = form.getTimeStartCreateSrch();
        String timeEndCreateSrch = form.getTimeEndCreateSrch();
        String timeStartUpdateSrch = form.getTimeStartUpdateSrch();
        String timeEndUpdateSrch = form.getTimeEndUpdateSrch();

        SellDailyDistributionExample example = new SellDailyDistributionExample();
        SellDailyDistributionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(businessName)) {
            criteria.andBusinessNameEqualTo(businessName);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        if (StringUtils.isNotBlank(timeStartCreateSrch) && StringUtils.isNotBlank(timeEndCreateSrch)) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(timeStartCreateSrch),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(timeStartCreateSrch),datetimeFormat));
        }
        if (StringUtils.isNotBlank(timeStartUpdateSrch) && StringUtils.isNotBlank(timeEndUpdateSrch)) {
            criteria.andUpdateTimeBetween(GetDate.str2Date(GetDate.getDayStart(timeStartUpdateSrch),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(timeEndUpdateSrch),datetimeFormat));
        }
        return sellDailyDistributionMapper.countByExample(example);
    }

    @Override
    public List<SellDailyDistribution> queryRecordList(EmailRecipientRequest form, Integer limitStart, Integer limitEnd) {
        // 业务名称
        String businessName = form.getBusinessName();
        //状态
        Integer status = form.getStatus();
        //创建开始时间
        String timeStartCreateSrch = form.getTimeStartCreateSrch();
        //创建结束时间
        String timeEndCreateSrch = form.getTimeEndCreateSrch();
        //更新开始时间
        String timeStartUpdateSrch = form.getTimeStartUpdateSrch();
        //更新结束时间
        String timeEndUpdateSrch = form.getTimeEndUpdateSrch();

        SellDailyDistributionExample example = new SellDailyDistributionExample();
        SellDailyDistributionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(businessName)) {
            criteria.andBusinessNameEqualTo(businessName);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (StringUtils.isNotBlank(timeStartCreateSrch) && StringUtils.isNotBlank(timeEndCreateSrch)) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(timeStartCreateSrch),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(timeStartCreateSrch),datetimeFormat));
        }
        if (StringUtils.isNotBlank(timeStartUpdateSrch) && StringUtils.isNotBlank(timeEndUpdateSrch)) {
            criteria.andUpdateTimeBetween(GetDate.str2Date(GetDate.getDayStart(timeStartUpdateSrch),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(timeEndUpdateSrch),datetimeFormat));
        }
        if (limitStart != null && limitEnd != null) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("update_time desc");
        return sellDailyDistributionMapper.selectByExample(example);
    }

    @Override
    public SellDailyDistribution queryRecordById(Integer id) {
        return sellDailyDistributionMapper.selectByPrimaryKey(id);
    }


    @Override
    public boolean updateRecord(EmailRecipientRequest form)  {
        SellDailyDistributionExample example = new SellDailyDistributionExample();
        SellDailyDistributionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(form.getId());
        SellDailyDistribution SellDailyDistribution = new SellDailyDistribution();
        if (StringUtils.isNotBlank(form.getBusinessName())) {
            SellDailyDistribution.setBusinessName(form.getBusinessName());
        }
        if (form.getTimePoint()!=null) {
            SellDailyDistribution.setTimePoint(form.getTimePoint());
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date parse = format.parse(form.getSendTime());
            if (form.getSendTime()!=null) {
                SellDailyDistribution.setTime(parse);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        if (StringUtils.isNotBlank(form.getEmail())) {
            SellDailyDistribution.setEmail(form.getEmail());
        }

        SellDailyDistribution.setUpdateName(form.getUpdateName());
        return sellDailyDistributionMapper.updateByExampleSelective(SellDailyDistribution, example) > 0;
    }

    /**
     * 禁用
     * @param form
     * @return
     */
    @Override
    public boolean updateForbidden(EmailRecipientRequest form) {
        SellDailyDistributionExample example = new SellDailyDistributionExample();
        SellDailyDistributionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(form.getId());
        SellDailyDistribution SellDailyDistribution = new SellDailyDistribution();
        SellDailyDistribution.setStatus(form.getStatus());
        SellDailyDistribution.setUpdateName(form.getUpdateName());
        return sellDailyDistributionMapper.updateByExampleSelective(SellDailyDistribution, example) > 0;
    }


    /**
     * 添加信息
     * @param form
     * @return
     */
    @Override
    public boolean insertRecord(EmailRecipientRequest form) {
        SellDailyDistribution SellDailyDistribution = new SellDailyDistribution();
        if (StringUtils.isNotBlank(form.getBusinessName())) {
            SellDailyDistribution.setBusinessName(form.getBusinessName());
        }
        if (form.getTimePoint()!=null) {
            SellDailyDistribution.setTimePoint(form.getTimePoint());
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date parse = format.parse(form.getSendTime());
            if (form.getSendTime()!=null) {
                SellDailyDistribution.setTime(parse);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        if (StringUtils.isNotBlank(form.getEmail())) {
            SellDailyDistribution.setEmail(form.getEmail());
        }
        SellDailyDistribution.setStatus(1);
        SellDailyDistribution.setCreateName(form.getCreateName());
        return sellDailyDistributionMapper.insert(SellDailyDistribution)> 0;
    }
}
