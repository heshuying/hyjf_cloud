/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    /**
     * 修改mongdb中百行报送状态
     * @return
     */
    public boolean	 updateUserConversion(String id){
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("submitStatus", 2);
        mongoTemplate.findAndModify(query, update, getEntityClass());
        return true;
    }
}
