package com.hyjf.am.statistics.mongo;

import com.hyjf.am.statistics.bean.AppChannelStatisticsDetail;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * @author xiasq
 * @version AppChannelStatisticsDetailDao, v0.1 2018/5/4 10:49
 */
@Repository
public class AppChannelStatisticsDetailDao extends BaseMongoDao<AppChannelStatisticsDetail> {

	public AppChannelStatisticsDetail findByUserId(int userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("userId").is(userId);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, getEntityClass());
	}

	@Override
	protected Class<AppChannelStatisticsDetail> getEntityClass() {
		return AppChannelStatisticsDetail.class;
	}
}
