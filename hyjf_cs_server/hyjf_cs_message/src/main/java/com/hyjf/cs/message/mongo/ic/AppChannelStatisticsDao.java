/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(timeStartSrch) && StringUtils.isNotBlank(timeEndSrch)) {
            Date startTime = GetDate.stringToDate2(request.getTimeStartSrch());
            Date endTime = GetDate.stringToDate2(request.getTimeEndSrch());
//            Integer begin = GetDate.dateString2Timestamp(timeStartSrch + " 00:00:00");
//            Integer end = GetDate.dateString2Timestamp(timeEndSrch + " 23:59:59");
            criteria.and("updateTime").gte(GetDate.getSomeDayStart(startTime)).lte(GetDate.getSomeDayEnd(endTime));
        }
        if (utmIdsSrch != null && utmIdsSrch.length > 0) {
            List<Integer> listInt = new ArrayList<>();
            List<String> sourceIds = Arrays.asList(utmIdsSrch);
            CollectionUtils.collect(sourceIds, o -> Integer.valueOf(String.valueOf(o)),listInt);
            criteria.and("sourceId").in(listInt);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group( "sourceId").sum("visitCount").as("visitCount").sum("registerCount").as("registerCount").sum("registerAttrCount").as("registerAttrCount").sum("openAccountCount").as("openAccountCount").sum("investNumber").as("investNumber").sum("cumulativeCharge").as("cumulativeCharge").sum("cumulativeInvest").as("cumulativeInvest").sum("hztInvestSum").as("hztInvestSum").sum("hxfInvestSum").as("hxfInvestSum").sum("htlInvestSum").as("htlInvestSum").sum("htjInvestSum").as("htjInvestSum").sum("rtbInvestSum").as("rtbInvestSum").sum("hzrInvestSum").as("hzrInvestSum").addToSet("channelName").as("channelName").addToSet("updateTime").as("updateTime").addToSet("sourceId").as("sourceId")
        );
        AggregationResults<AppChannelStatistics> ar = mongoTemplate.aggregate(aggregation, getEntityClass(), AppChannelStatistics.class);
        List<AppChannelStatistics> result = ar.getMappedResults();

        result = paging(request,result);

        return result;
    }

    public List<AppChannelStatistics> paging(AppChannelStatisticsRequest request,List<AppChannelStatistics> result){
        int current=request.getCurrPage(); //页码
        int pageSize=request.getPageSize(); //每页显示的数量
        int totalCount=result.size();
        int pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

        if(current < 1){
            current = 1;
        }
        int start=(current-1) * pageSize;
        int end = Math.min(totalCount, current * pageSize);

        if(pageCount >= current){
            result=result.subList(start,end);
        }else{
            result = new ArrayList<>();
        }

        return result;
    }

    public AggregationResults<Map> countChannelStatistics(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, getEntityClass(), Map.class);
    }

    public AggregationResults<AppChannelStatistics> exportList(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, getEntityClass(), AppChannelStatistics.class);
    }
}
