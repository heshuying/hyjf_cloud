package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankExclusiveLog;

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
