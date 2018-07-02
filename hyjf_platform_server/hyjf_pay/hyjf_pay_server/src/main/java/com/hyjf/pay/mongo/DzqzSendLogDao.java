package com.hyjf.pay.mongo;

import com.hyjf.pay.entity.DzqzSendLog;
import org.springframework.stereotype.Repository;
import com.hyjf.pay.base.BaseMongoDao;
@Repository
public class DzqzSendLogDao extends BaseMongoDao<DzqzSendLog>{


    @Override
    protected Class<DzqzSendLog> getEntityClass() {
        return DzqzSendLog.class;
    }
}
