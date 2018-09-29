/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;


/**
 * @author fuqiang
 * @version AppChannelStatisticsDao, v0.1 2018/7/2 10:53
 */
@Repository
public class AppChannelStatisticsDao extends BaseMongoDao<AppChannelStatistics> {
    @Override
    protected Class<AppChannelStatistics> getEntityClass() {
        return AppChannelStatistics.class;
    }

    public List<AppChannelStatistics> findChannelStatistics(AppChannelStatisticsRequest request) {
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
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            CollectionUtils.collect(sourceIds, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Integer.valueOf(String.valueOf(o));
                }
            },listInt);
            criteria.and("sourceId").in(listInt);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group( "sourceId").sum("visitCount").as("visitCount").sum("registerCount").as("registerCount").sum("registerAttrCount").as("registerAttrCount").sum("openAccountCount").as("openAccountCount").sum("investNumber").as("investNumber").sum("cumulativeCharge").as("cumulativeCharge").sum("cumulativeInvest").as("cumulativeInvest").sum("hztInvestSum").as("hztInvestSum").sum("hxfInvestSum").as("hxfInvestSum").sum("htlInvestSum").as("htlInvestSum").sum("htjInvestSum").as("htjInvestSum").sum("rtbInvestSum").as("rtbInvestSum").sum("hzrInvestSum").as("hzrInvestSum").addToSet("channelName").as("channelName").addToSet("updateTime").as("updateTime").addToSet("sourceId").as("sourceId")
        );
        AggregationResults<AppChannelStatistics> ar = mongoTemplate.aggregate(aggregation, getEntityClass(), AppChannelStatistics.class);
        List<AppChannelStatistics> result = ar.getMappedResults();


        return result;
    }

    public AggregationResults<Map> countChannelStatistics(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, getEntityClass(), Map.class);
    }

    public AggregationResults<AppChannelStatistics> exportList(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, getEntityClass(), AppChannelStatistics.class);
    }
}
