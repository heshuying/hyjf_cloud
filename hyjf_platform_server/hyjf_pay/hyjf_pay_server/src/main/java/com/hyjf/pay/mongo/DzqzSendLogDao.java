package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.DzqzSendLog;


@Repository
public class DzqzSendLogDao extends BaseMongoDao<DzqzSendLog>{
	
    private static final String SENDLOG = "dzqzsendlog";


    @Override
    protected Class<DzqzSendLog> getEntityClass() {
        return DzqzSendLog.class;
    }

	@Override
	protected String getTableName() {
		return SENDLOG;
	}
}
