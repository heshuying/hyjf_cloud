package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowUserStatisticMongDao extends BaseMongoDao<BorrowUserStatistic> {

	
	@Override
	protected Class<BorrowUserStatistic> getEntityClass() {
		return BorrowUserStatistic.class;
	}
	@Override
	public List<BorrowUserStatistic> find(Query query){
		return this.mongoTemplate.find(query, getEntityClass());
	}
}
