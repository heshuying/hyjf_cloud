package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.ic.OperationGroupReport;
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
