package com.hyjf.pay.mongo;

import com.hyjf.pay.base.BaseMongoDao;
import com.hyjf.pay.entity.DzqzReturnLog;
import org.springframework.stereotype.Repository;

@Repository
public class DzqzReturnLogDao extends BaseMongoDao<DzqzReturnLog>{

    @Override
    protected Class<DzqzReturnLog> getEntityClass() {
        return DzqzReturnLog.class;
    }
}
