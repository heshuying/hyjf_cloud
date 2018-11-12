package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationMongDao extends BaseMongoDao<OperationReport> {

	
	@Override
	protected Class<OperationReport> getEntityClass() {
		return OperationReport.class;
	}
	@Override
	public List<OperationReport> find(Query query){
		return this.mongoTemplate.find(query, getEntityClass());
	}
}
