package com.hyjf.cs.message.mongo.ic.report;

import com.hyjf.cs.message.bean.ic.report.OperationGroupReport;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 统计性别，年龄，地域数据
 * @author tanyy
 *
 */
@Repository
public class OperationMongoGroupDao extends BaseMongoDao<OperationGroupReport> {

	
	@Override
	protected Class<OperationGroupReport> getEntityClass() {
		return OperationGroupReport.class;
	}

}
