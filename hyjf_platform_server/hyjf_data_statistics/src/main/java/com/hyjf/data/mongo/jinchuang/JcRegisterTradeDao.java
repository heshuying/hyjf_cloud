/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.mongo.jinchuang;

import com.hyjf.common.util.GetDate;
import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.mongo.BaseMongoDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaoyong
 * @version JcRegisterTradeDao, v0.1 2019/6/20 15:00
 */
@Repository
public class JcRegisterTradeDao extends BaseMongoDao<JcRegisterTrade> {
    @Override
    protected Class<JcRegisterTrade> getEntityClass() {
        return JcRegisterTrade.class;
    }

    public List<JcRegisterTrade> getRegisterTrade() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.limit(12);
        return mongoTemplate.find(query, getEntityClass());
    }

    /**
     * 获取当月数据
     * @return
     */
    public JcRegisterTrade getRegisterTradeByNowMonth(String month) {
        Query query = new Query();
        CriteriaDefinition criteria = Criteria.where("time").is(month);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query,getEntityClass());
    }

}
