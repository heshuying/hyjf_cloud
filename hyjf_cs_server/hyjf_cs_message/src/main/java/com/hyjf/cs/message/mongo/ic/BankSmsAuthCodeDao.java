/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.BankSmsAuthCode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version BankSmsAuthCodeDao, v0.1 2018/6/25 10:49
 */
@Repository
public class BankSmsAuthCodeDao extends BaseMongoDao<BankSmsAuthCode> {
    @Override
    protected Class<BankSmsAuthCode> getEntityClass() {
        return BankSmsAuthCode.class;
    }

    public BankSmsAuthCode selectBankSmsSeq(int userId,String srvTxCode) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId).and("srvTxCode").is(srvTxCode);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, getEntityClass());
    }
}
