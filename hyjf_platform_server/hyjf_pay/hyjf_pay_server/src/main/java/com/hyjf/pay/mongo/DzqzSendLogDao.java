package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.DzqzSendLog;
import org.springframework.stereotype.Repository;


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
