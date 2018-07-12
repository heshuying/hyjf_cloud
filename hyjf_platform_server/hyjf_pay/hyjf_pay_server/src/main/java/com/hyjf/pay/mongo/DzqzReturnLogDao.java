package com.hyjf.pay.mongo;

import org.springframework.stereotype.Repository;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.DzqzReturnLog;

@Repository
public class DzqzReturnLogDao extends BaseMongoDao<DzqzReturnLog>{

    private static final String BACKLOG = "dzqzreturnlog";

    @Override
    protected Class<DzqzReturnLog> getEntityClass() {
        return DzqzReturnLog.class;
    }

	@Override
	protected String getTableName() {
		return BACKLOG;
	}
}
