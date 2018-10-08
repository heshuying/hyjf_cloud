package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.DzqzReturnLog;
import org.springframework.stereotype.Repository;

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
