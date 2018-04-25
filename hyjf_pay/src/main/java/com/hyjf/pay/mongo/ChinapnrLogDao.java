package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;
import com.hyjf.pay.base.BaseMongoDao;



@Repository
public class ChinapnrLogDao extends BaseMongoDao<ChinapnrLog>{

	@Override
	protected Class<ChinapnrLog> getEntityClass() {
		return ChinapnrLog.class;
	}

	
}
