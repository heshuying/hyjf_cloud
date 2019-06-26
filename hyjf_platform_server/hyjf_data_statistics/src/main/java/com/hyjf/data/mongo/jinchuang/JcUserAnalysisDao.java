/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.bean.jinchuang.JcUserAnalysis;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaoyong
 * @version JcUserAnalysisDao, v0.1 2019/6/20 11:10
 */
@Repository
public class JcUserAnalysisDao extends BaseMongoDao<JcUserAnalysis> {
    @Override
    protected Class<JcUserAnalysis> getEntityClass() {
        return JcUserAnalysis.class;
    }

    public List<JcUserAnalysis> getUserAnalysis() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(1);
        return mongoTemplate.find(query, getEntityClass());
    }
}
