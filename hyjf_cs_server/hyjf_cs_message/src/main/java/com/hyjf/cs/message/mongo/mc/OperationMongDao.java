package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationReportEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationMongDao extends BaseMongoDao<OperationReportEntity> {

	
	@Override
	protected Class<OperationReportEntity> getEntityClass() {
		return OperationReportEntity.class;
	}
	@Override
	public List<OperationReportEntity> find(Query query){
		return this.mongoTemplate.find(query, getEntityClass());
	}
}
