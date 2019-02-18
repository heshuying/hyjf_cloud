/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.channel.impl;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDao;
import com.hyjf.cs.message.mongo.ic.PcChannelStatisticsDao;
import com.hyjf.cs.message.service.channel.ChannelStatisticsService;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;


/**
 * @author yaoyong
 * @version ChannelStatisticsServiceImpl, v0.1 2018/9/21 12:34
 */
@Service
public class ChannelStatisticsServiceImpl implements ChannelStatisticsService {

    @Autowired
    private AppChannelStatisticsDao appChannelStatisticsDao;
    @Autowired
    private PcChannelStatisticsDao pcChannelStatisticsDao;

    @Override
    public List<AppChannelStatistics> findChannelStatistics(AppChannelStatisticsRequest request) {
        return appChannelStatisticsDao.findChannelStatistics(request);
    }

    @Override
    public int queryChannelStatisticsCount(AppChannelStatisticsRequest request) {
        String timeStartSrch = request.getTimeStartSrch();
        String timeEndSrch = request.getTimeEndSrch();
        String[] utmIdsSrch = request.getUtmIdsSrch();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(timeStartSrch) && StringUtils.isNotBlank(timeEndSrch)) {
            Date startTime = GetDate.stringToDate2(request.getTimeStartSrch());
            Date endTime = GetDate.stringToDate2(request.getTimeEndSrch());
            criteria.and("updateTime").gte(GetDate.getSomeDayStart(startTime)).lte(GetDate.getSomeDayEnd(endTime));
        }
        if (utmIdsSrch != null && utmIdsSrch.length > 0) {
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            org.apache.commons.collections.CollectionUtils.collect(sourceIds, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Integer.valueOf(String.valueOf(o));
                }
            },listInt);
            criteria.and("sourceId").in(listInt);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group("sourceId").count().as("count")
        );
        AggregationResults<Map> ar = appChannelStatisticsDao.countChannelStatistics(aggregation);
        List<Map> result = ar.getMappedResults();
        int count = result.size();
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
        if (utmIdsSrch != null && utmIdsSrch.length > 0) {
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            org.apache.commons.collections.CollectionUtils.collect(sourceIds, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Integer.valueOf(String.valueOf(o));
                }
            },listInt);
            criteria.and("sourceId").in(listInt);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group("sourceId").sum("visitCount").as("visitCount").sum("registerCount").as("registerCount").sum("registerAttrCount").as("registerAttrCount").sum("openAccountCount").as("openAccountCount").sum("investNumber").as("investNumber").sum("cumulativeCharge").as("cumulativeCharge").sum("cumulativeInvest").as("cumulativeInvest").sum("hztInvestSum").as("hztInvestSum").sum("hxfInvestSum").as("hxfInvestSum").sum("htlInvestSum").as("htlInvestSum").sum("htjInvestSum").as("htjInvestSum").sum("rtbInvestSum").as("rtbInvestSum").sum("hzrInvestSum").as("hzrInvestSum").sum("accountNumberIos").as("accountNumberIos").sum("accountNumberPc").as("accountNumberPc").sum("accountNumberAndroid").as("accountNumberAndroid").sum("accountNumberWechat").as("accountNumberWechat").sum("tenderNumberAndroid").as("tenderNumberAndroid").sum("tenderNumberIos").as("tenderNumberIos").sum("tenderNumberPc").as("tenderNumberPc").sum("tenderNumberWechat").as("tenderNumberWechat").sum("cumulativeAttrCharge").as("cumulativeAttrCharge").sum("cumulativeAttrInvest").as("cumulativeAttrInvest").sum("openAccountAttrCount").as("openAccountAttrCount").sum("investAttrNumber").as("investAttrNumber").addToSet("channelName").as("channelName").addToSet("sourceId").as("sourceId").addToSet("updateTime").as("updateTime")
        );
        AggregationResults<AppChannelStatistics> ar = appChannelStatisticsDao.exportList(aggregation);
        List<AppChannelStatistics> result = ar.getMappedResults();
//        result = appChannelStatisticsDao.paging(request,result);
        return result;
    }

    @Override
    public List<PcChannelStatistics> searchPcChannelStatisticsList(PcChannelStatisticsRequest request) {
        /*Query query = new Query();
        Criteria criteria = new Criteria();
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        String[] utmIdsSrch = request.getUtmIdsSrch();
        if (startTime != null && endTime != null) {
            criteria.and("addTime").gte(GetDate.getSomeDayStart(startTime)).lte(GetDate.getSomeDayEnd(endTime));
        }
        if (utmIdsSrch != null && utmIdsSrch.length > 0) {
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            org.apache.commons.collections.CollectionUtils.collect(sourceIds, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Integer.valueOf(String.valueOf(o));
                }
            },listInt);
            criteria.and("sourceId").in(listInt);
        }
      *//*  query.addCriteria(criteria);

        int currPage = request.getCurrPage();
        int pageSize = request.getPageSize();
        int limitStart = (currPage - 1) * pageSize;
        query.skip(limitStart).limit(pageSize);

        query.with(new Sort(Sort.Direction.DESC, "_id"));*//*
        return pcChannelStatisticsDao.find(query);*/
        return pcChannelStatisticsDao.searchPcChannelStatisticsList(request);

    }

    @Override
    public int selectCount(PcChannelStatisticsRequest request) {
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        String[] utmIdsSrch = request.getUtmIdsSrch();
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (startTime != null && endTime != null) {
            criteria.and("addTime").gte(GetDate.getSomeDayStart(startTime)).lte(GetDate.getSomeDayEnd(endTime));

        }
        if (utmIdsSrch != null && utmIdsSrch.length > 0) {
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            org.apache.commons.collections.CollectionUtils.collect(sourceIds, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Integer.valueOf(String.valueOf(o));
                }
            },listInt);
            criteria.and("sourceId").in(listInt);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group("sourceId").count().as("count")
        );
        AggregationResults<Map> ar = pcChannelStatisticsDao.countChannelStatistics(aggregation);
        List<Map> result = ar.getMappedResults();
        int count = result.size();
        return count;

    }
}
