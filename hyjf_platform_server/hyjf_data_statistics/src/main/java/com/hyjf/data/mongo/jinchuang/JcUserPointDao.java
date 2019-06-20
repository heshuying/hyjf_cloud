/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.JcUserPoint;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaoyong
 * @version JcUserPointDao, v0.1 2019/6/19 15:24
 */
@Repository
public class JcUserPointDao extends BaseMongoDao<JcUserPoint> {

    @Override
    protected Class<JcUserPoint> getEntityClass() {
        return JcUserPoint.class;
    }

    public JcUserPoint getUserPoint() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(1);
        return mongoTemplate.findOne(new Query(), getEntityClass());
    }
}
