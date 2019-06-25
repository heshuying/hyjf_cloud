/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yaoyong
 * @version JcUserConversionDao, v0.1 2019/6/19 14:53
 */
@Repository
public class JcUserConversionDao extends BaseMongoDao<JcUserConversion> {

    @Override
    protected Class<JcUserConversion> getEntityClass() {
        return JcUserConversion.class;
    }

    public JcUserConversion getUserConversion() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(1);
        return mongoTemplate.findOne(query, getEntityClass());
    }

    public void saveUserConversion(JcUserConversion entity){
        mongoTemplate.save(entity);
    }
}
