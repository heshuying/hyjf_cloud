package com.hyjf.pay.mongo.impl;

import com.hyjf.pay.bean.BankLog;
import com.hyjf.pay.mongo.BankLogMongoDao;
import com.hyjf.pay.mongo.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author xiasq
 * @version BankLogMongoDaoImpl, v0.1 2018/4/19 12:08
 */

@Repository
public class BankLogMongoDaoImpl extends BaseMongoDao implements BankLogMongoDao {

    @Override
    public int save(BankLog bankLog) {
        mongoTemplate.insert(bankLog);
        return 1;
    }

    @Override
    public BankLog getOneBeanById(int userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
        BankLog demoBean = mongoTemplate.findOne(query, BankLog.class);
        return demoBean;
    }
}
