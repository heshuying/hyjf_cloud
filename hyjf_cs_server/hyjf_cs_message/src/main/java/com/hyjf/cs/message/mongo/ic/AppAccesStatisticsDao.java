package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.AppAccesStatistics;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author xiasq
 * @version AppAccessStaticsDao, v0.1 2018/6/19 16:47
 */
@Repository
public class AppAccesStatisticsDao extends BaseMongoDao<AppAccesStatistics> {

    @Override
    protected Class<AppAccesStatistics> getEntityClass() {
        return AppAccesStatistics.class;
    }

    public List<AppAccesStatistics> getAppAccesStatistics(Query query){
        return mongoTemplate.find(query,getEntityClass());
    }

    public AggregationResults<Map> getAppAccesStatisticsList(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, getEntityClass(), Map.class);
    }

}
