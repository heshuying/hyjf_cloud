package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.BankSendlog;
import org.springframework.stereotype.Repository;

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
