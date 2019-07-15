/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.data.mongo.BaseMongoDao;
import com.hyjf.data.bean.jinchuang.InterfaceConfiguration;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * add by cwyang
 * 接口地址配置类
 */
@Repository
public class InterfaceConfigurationDao extends BaseMongoDao<InterfaceConfiguration> {
    @Override
    protected Class<InterfaceConfiguration> getEntityClass() {
        return InterfaceConfiguration.class;
    }

    public InterfaceConfiguration getInterfaceConfigurationByInterfceNmeAndMehtod(String interfaceName,String methodName) {
        Query query = new Query();
        CriteriaDefinition cri = Criteria.where("interfaceName").is(interfaceName).and("methodName").is(methodName);
        query.addCriteria(cri);
        return mongoTemplate.findOne(query, getEntityClass());
    }

}
