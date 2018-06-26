/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mongo;

import com.hyjf.am.statistics.bean.DirectionalTransferAssociatedRecords;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingqing
 * @version DirectionalTransferAssociatedRecordsDao, v0.1 2018/6/25 10:18
 */
@Repository
public class DirectionalTransferAssociatedRecordsDao extends BaseMongoDao<DirectionalTransferAssociatedRecords> {
    @Override
    protected Class<DirectionalTransferAssociatedRecords> getEntityClass() {
        return DirectionalTransferAssociatedRecords.class;
    }

    public DirectionalTransferAssociatedRecords findByUserId(int userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, getEntityClass());
    }
}
