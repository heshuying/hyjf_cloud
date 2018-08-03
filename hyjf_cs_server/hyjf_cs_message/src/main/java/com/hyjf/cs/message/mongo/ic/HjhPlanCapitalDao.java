package com.hyjf.cs.message.mongo.ic;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
@Repository
public class HjhPlanCapitalDao extends BaseMongoDao<HjhPlanCapital> {

    @Override
    protected Class<HjhPlanCapital> getEntityClass() {
        return HjhPlanCapital.class;
    }

    public long getCount(HjhPlanCapitalCustomizeVO hjhPlanCapitalVO){
        Query query = new Query();
        Criteria criteria = createCriteria(hjhPlanCapitalVO);
        query.addCriteria(criteria);
        return  mongoTemplate.count(query, getEntityClass());
    }

    public List<HjhPlanCapital> findAllList(HjhPlanCapitalCustomizeVO hjhPlanCapitalVO){
        Query query = new Query();
        Criteria criteria = createCriteria(hjhPlanCapitalVO);
        query.addCriteria(criteria);

        return mongoTemplate.find(query, getEntityClass());
    }

    public Criteria createCriteria(HjhPlanCapitalCustomizeVO hjhPlanCapitalVO){
        Criteria criteria;
        if (null != hjhPlanCapitalVO){
            criteria = Criteria.where("id").gt(0);
            if (hjhPlanCapitalVO.getPlanName() != null){
                criteria = criteria.and("planName").is(hjhPlanCapitalVO.getPlanName());
            }
            return criteria;
        }
        return new Criteria();
    }
}
