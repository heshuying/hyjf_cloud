package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankExclusiveLog;
import org.springframework.stereotype.Repository;

@Repository
public class BankExclusiveLogDao extends BaseMongoDao<BankExclusiveLog>{
	
    private static final String EXCLUSENDLOG = "bankexclusivelog";

	@Override
	protected Class<BankExclusiveLog> getEntityClass() {
		return BankExclusiveLog.class;
	}

	@Override
	protected String getTableName() {
		return EXCLUSENDLOG;
	}
	
}
