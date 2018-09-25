/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.channel.impl;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDao;
import com.hyjf.cs.message.service.channel.ChannelStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;


/**
 * @author yaoyong
 * @version ChannelStatisticsServiceImpl, v0.1 2018/9/21 12:34
 */
@Service
public class ChannelStatisticsServiceImpl implements ChannelStatisticsService {

    @Autowired
    private AppChannelStatisticsDao appChannelStatisticsDao;

    @Override
    public List<AppChannelStatistics> findChannelStatistics(AppChannelStatisticsRequest request) {
        return appChannelStatisticsDao.findChannelStatistics(request);
    }

    @Override
    public int queryChannelStatisticsCount(AppChannelStatisticsRequest request) {
        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String[] utmIdsSrch = request.getUtmIdsSrch();
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(timeStartSrch) && StringUtils.isNotBlank(timeEndSrch)) {
            Integer begin = GetDate.dateString2Timestamp(timeStartSrch + " 00:00:00");
            Integer end = GetDate.dateString2Timestamp(timeEndSrch + " 23:59:59");
            criteria.and("updateTime").gte(begin).lte(end);
        }
        if (utmIdsSrch.length > 0) {
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            criteria.in(sourceIds);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group("id").count().as("count")
        );
        AggregationResults<Integer> ar = appChannelStatisticsDao.countChannelStatistics(aggregation);
        int count = 0;
        if (ar.getUniqueMappedResult() != null) {
            count = ar.getUniqueMappedResult();
        }
        return count;
    }

    @Override
    public List<AppChannelStatistics> exportList(AppChannelStatisticsRequest request) {
        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String[] utmIdsSrch = request.getUtmIdsSrch();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(timeStartSrch) && StringUtils.isNotBlank(timeEndSrch)) {
            Integer begin = GetDate.dateString2Timestamp(timeStartSrch + " 00:00:00");
            Integer end = GetDate.dateString2Timestamp(timeEndSrch + " 23:59:59");
            criteria.and("updateTime").gte(begin).lte(end);
        }
        if (utmIdsSrch.length > 0) {
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            criteria.in(sourceIds);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group("channelName", "sourceId", "updateTime").sum("visitCount").as("visitCount").sum("registerCount").as("registerCount").sum("registerAttrCount").as("registerAttrCount").sum("openAccountCount").as("openAccountCount").sum("investNumber").as("investNumber").sum("cumulativeCharge").as("cumulativeCharge").sum("cumulativeInvest").as("cumulativeInvest").sum("hztInvestSum").as("hztInvestSum").sum("hxfInvestSum").as("hxfInvestSum").sum("htlInvestSum").as("htlInvestSum").sum("htjInvestSum").as("htjInvestSum").sum("rtbInvestSum").as("rtbInvestSum").sum("hzrInvestSum").as("hzrInvestSum").sum("accountNumberIos").as("accountNumberIos").sum("accountNumberPc").as("accountNumberPc").sum("accountNumberAndroid").as("accountNumberAndroid").sum("accountNumberWechat").as("accountNumberWechat").sum("tenderNumberAndroid").as("tenderNumberAndroid").sum("tenderNumberIos").as("tenderNumberIos").sum("tenderNumberPc").as("tenderNumberPc").sum("tenderNumberWechat").as("tenderNumberWechat").sum("cumulativeAttrCharge").as("cumulativeAttrCharge").sum("cumulativeAttrInvest").as("cumulativeAttrInvest").sum("openAccountAttrCount").as("openAccountAttrCount").sum("investAttrNumber").as("investAttrNumber")
        );
        AggregationResults<AppChannelStatistics> ar = appChannelStatisticsDao.exportList(aggregation);
        List<AppChannelStatistics> result = ar.getMappedResults();
        return result;
    }
}
