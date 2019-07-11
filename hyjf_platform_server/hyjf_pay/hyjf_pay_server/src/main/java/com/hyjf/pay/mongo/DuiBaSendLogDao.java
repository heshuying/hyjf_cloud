package com.hyjf.pay.mongo;

import com.hyjf.pay.entity.DuiBaSendLog;
import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;

@Repository
public class DuiBaSendLogDao extends BaseMongoDao<DuiBaSendLog>{
    private static final String BACKLOG = "ht_duiba_send_log";

	@Override
	protected Class<DuiBaSendLog> getEntityClass() {
		return DuiBaSendLog.class;
	}

	@Override
	protected String getTableName() {
		return BACKLOG;
	}
	
}
