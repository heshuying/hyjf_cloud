/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.message.JcCustomerServerRequest;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.JcCustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yaoyong
 * @version JcCustomerServiceDao, v0.1 2019/6/20 15:25
 */
@Repository
public class JcCustomerServiceDao extends BaseMongoDao<JcCustomerService> {
    @Override
    protected Class<JcCustomerService> getEntityClass() {
        return JcCustomerService.class;
    }

    public JcCustomerService getCustomerService() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.findOne(query, getEntityClass());
    }

    public long countServerList(JcCustomerServerRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (request.getStartTime() != null || request.getEndTime() != null) {
            int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartTime() + " 00:00:00");
            int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndTime() + " 23:59:59");
            criteria.and("updateTime").gte(startTime).lte(endTime);
        }
        query.addCriteria(criteria);
        return mongoTemplate.count(query, getEntityClass());
    }

    public List<JcCustomerService> getServerList(JcCustomerServerRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (request.getStartTime() != null || request.getEndTime() != null) {
            int startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getStartTime() + " 00:00:00");
            int endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(request.getEndTime() + " 23:59:59");
            criteria.and("updateTime").gte(startTime).lte(endTime);
        }
        int currPage = request.getCurrPage();
        int pageSize = request.getPageSize();
        int limitStart = (currPage - 1) * pageSize;
        int limitEnd = pageSize;
        query.addCriteria(criteria);
        query.skip(limitStart).limit(limitEnd);
        query.with(new Sort(Sort.Direction.DESC, "updateTime"));
        return mongoTemplate.find(query, getEntityClass());
    }

    public JcCustomerService getCustomerServer(String id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (id != null) {
            criteria.and("_id").is(id);
        }
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, getEntityClass());
    }
}
