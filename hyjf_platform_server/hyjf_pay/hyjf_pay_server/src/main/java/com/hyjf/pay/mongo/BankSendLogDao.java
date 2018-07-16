package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankSendlog;

@Repository
public class BankSendLogDao extends BaseMongoDao<BankSendlog>{
	
	private static final String SENDLOG = "banksendlog";

	@Override
	protected Class<BankSendlog> getEntityClass() {
		return BankSendlog.class;
	}

	@Override
	protected String getTableName() {
		return SENDLOG;
	}

	
}
