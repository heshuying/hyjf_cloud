/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.cs.message.bean.ic.BankExclusiveLog;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version BankExclusiveLogDao, v0.1 2018/8/3 14:13
 */
@Repository
public class BankExclusiveLogDao extends BaseMongoDao<BankExclusiveLog> {

    @Override
    protected Class<BankExclusiveLog> getEntityClass() {
        return BankExclusiveLog.class;
    }
    /**
     * 查询检证日志
     *
     * @param orderId
     * @return
     */
    public BankExclusiveLog selectChinapnrExclusiveLogByOrderId(String orderId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("ordid").is(orderId);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query,getEntityClass());
    }


}
