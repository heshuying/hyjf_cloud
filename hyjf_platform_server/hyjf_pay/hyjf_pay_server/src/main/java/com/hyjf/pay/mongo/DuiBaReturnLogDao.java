package com.hyjf.pay.mongo;

import com.hyjf.pay.entity.DuiBaReturnLog;
import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;

@Repository
public class DuiBaReturnLogDao extends BaseMongoDao<DuiBaReturnLog>{
    private static final String BACKLOG = "ht_duiba_return_log";

	@Override
	protected Class<DuiBaReturnLog> getEntityClass() {
		return DuiBaReturnLog.class;
	}

	@Override
	protected String getTableName() {
		return BACKLOG;
	}
	
}
