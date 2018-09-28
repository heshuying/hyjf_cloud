package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankLog;
import org.springframework.stereotype.Repository;

@Repository
public class BankLogDao extends BaseMongoDao<BankLog>{
    private static final String BACKLOG = "banklog";

	@Override
	protected Class<BankLog> getEntityClass() {
		return BankLog.class;
	}

	@Override
	protected String getTableName() {
		return BACKLOG;
	}
	
}
