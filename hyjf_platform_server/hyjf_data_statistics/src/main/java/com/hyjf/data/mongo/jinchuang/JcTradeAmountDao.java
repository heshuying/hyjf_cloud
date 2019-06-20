/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @author yaoyong
 * @version JcTradeAmountDao, v0.1 2019/6/20 14:45
 */
@Repository
public class JcTradeAmountDao extends BaseMongoDao<JcTradeAmount> {
    @Override
    protected Class<JcTradeAmount> getEntityClass() {
        return JcTradeAmount.class;
    }

    public JcTradeAmount getTradeAmount() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(1);
        return mongoTemplate.findOne(query, getEntityClass());
    }
}
