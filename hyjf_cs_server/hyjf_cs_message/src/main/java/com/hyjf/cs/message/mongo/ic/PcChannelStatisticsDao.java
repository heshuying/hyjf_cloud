/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.AppChannelStatistics;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;


/**
 * @author fuqiang
 * @version PcChannelStatisticsDao, v0.1 2018/7/2 10:15
 */
@Repository
public class PcChannelStatisticsDao extends BaseMongoDao<PcChannelStatistics> {
    @Override
    protected Class<PcChannelStatistics> getEntityClass() {
        return PcChannelStatistics.class;
    }

    public List<PcChannelStatistics> searchPcChannelStatisticsList(PcChannelStatisticsRequest request) {
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
       Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                Aggregation.group( "sourceId")
                        .sum("accessNumber").as("accessNumber")
                        .sum("registNumber").as("registNumber")
                        .sum("openAccountNumber").as("openAccountNumber")
                        .sum("tenderNumber").as("tenderNumber")
                        .sum("cumulativeRecharge").as("cumulativeRecharge")
                        .sum("cumulativeInvestment").as("cumulativeInvestment")
                        .sum("hztTenderPrice").as("hztTenderPrice")
                        .sum("hxfTenderPrice").as("hxfTenderPrice")
                        .sum("htlTenderPrice").as("htlTenderPrice")
                        .sum("htjTenderPrice").as("htjTenderPrice")
                        .sum("rtbTenderPrice").as("rtbTenderPrice")
                        .sum("hzrTenderPrice").as("hzrTenderPrice")
                        .addToSet("sourceName").as("sourceName")
                        .addToSet("sourceId").as("sourceId")
        );
        AggregationResults<PcChannelStatistics> ar = mongoTemplate.aggregate(aggregation, getEntityClass(), PcChannelStatistics.class);
        List<PcChannelStatistics> result = ar.getMappedResults();

        result = paging(request,result);

        return result;
    }

    public List<PcChannelStatistics> paging(PcChannelStatisticsRequest request,List<PcChannelStatistics> result){
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
}
