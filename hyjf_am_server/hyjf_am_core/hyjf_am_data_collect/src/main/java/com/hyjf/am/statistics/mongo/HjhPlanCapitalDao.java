package com.hyjf.am.statistics.mongo;

import com.hyjf.am.statistics.bean.HjhPlanCapital;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalVO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public long getCount(HjhPlanCapitalVO hjhPlanCapitalVO){
        Query query = new Query();
        Criteria criteria = createCriteria(hjhPlanCapitalVO);
        query.addCriteria(criteria);
        return  mongoTemplate.count(query, getEntityClass());
    }

    public List<HjhPlanCapital> findAllList(HjhPlanCapitalVO hjhPlanCapitalVO){
        Query query = new Query();
        Criteria criteria = createCriteria(hjhPlanCapitalVO);
        query.addCriteria(criteria);

        return mongoTemplate.find(query, getEntityClass());
    }

    public Criteria createCriteria(HjhPlanCapitalVO hjhPlanCapitalVO){
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
