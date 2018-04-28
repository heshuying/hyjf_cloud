package com.hyjf.pay.mongo;

import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import org.springframework.stereotype.Repository;
import com.hyjf.pay.base.BaseMongoDao;

@Repository
public class ChinapnrExclusiveLogDao extends BaseMongoDao<ChinapnrExclusiveLog>{
	

	@Override
	protected Class<ChinapnrExclusiveLog> getEntityClass() {
		return ChinapnrExclusiveLog.class;
	}
	
}
