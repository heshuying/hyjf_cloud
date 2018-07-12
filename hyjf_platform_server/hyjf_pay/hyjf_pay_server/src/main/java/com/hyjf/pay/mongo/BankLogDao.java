package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankLog;

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
