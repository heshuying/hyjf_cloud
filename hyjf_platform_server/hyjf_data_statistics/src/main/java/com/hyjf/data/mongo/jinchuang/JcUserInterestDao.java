/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.JcUserInterest;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaoyong
 * @version JcUserInterestDao, v0.1 2019/6/27 9:50
 */
@Repository
public class JcUserInterestDao extends BaseMongoDao<JcUserInterest> {
    @Override
    protected Class<JcUserInterest> getEntityClass() {
        return JcUserInterest.class;
    }

    public List<JcUserInterest> getUserInterest() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(6);
        return mongoTemplate.find(query, getEntityClass());
    }
}
