/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import com.hyjf.cs.message.bean.mc.BankExclusiveLog;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author zhangqingqing
 * @version BankReturnConfig, v0.1 2018/8/3 14:13
 */
public class BankReturnConfigDao extends BaseMongoDao<BankExclusiveLog> {

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
