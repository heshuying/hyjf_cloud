package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;
import com.hyjf.pay.base.BaseMongoDao;

@Repository
public class ChinapnrSendLogDao extends BaseMongoDao<ChinapnrSendlog>{

	@Override
	protected Class<ChinapnrSendlog> getEntityClass() {
		return ChinapnrSendlog.class;
	}

	
}
