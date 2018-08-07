package com.hyjf.cs.message.mongo.ic;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import com.hyjf.common.util.GetDate;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
@Repository
public class HjhPlanCapitalDao extends BaseMongoDao<HjhPlanCapitalVO> {

    @Override
    protected Class<HjhPlanCapitalVO> getEntityClass() {
        return HjhPlanCapitalVO.class;
    }

    public long getCount(HjhPlanCapitalCustomizeVO hjhPlanCapitalVO){
        Query query = new Query();
        Criteria criteria = createCriteria(hjhPlanCapitalVO);
        query.addCriteria(criteria);
        return  mongoTemplate.count(query, getEntityClass());
    }

    public List<HjhPlanCapitalVO> findAllList(HjhPlanCapitalCustomizeVO hjhPlanCapitalVO){
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

    /**
     * 删除汇计划资本按天统计及预估表的昨天今天及之后9天的记录
     * @param dateFrom
     * @param dateTo
     */
    public void deleteHjhPlanCapital(Date dateFrom, Date dateTo){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("date").gte(dateFrom);
        criteria.and("date").lte(dateTo);
        query.addCriteria(criteria);
        this.del(query);
    }

    /**
     * 检索（汇计划资本按天统计及预估表）
     * @param hjhPlanCapital
     * @return
     */
    public List<HjhPlanCapitalVO> selectHjhPlanCapitalList(HjhPlanCapitalVO hjhPlanCapital) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("date").is(hjhPlanCapital.getDate());
        criteria.and("planNid").is(hjhPlanCapital.getPlanNid());
        query.addCriteria(criteria);
        return this.find(query);
    }

    /**
     * 更新记录（汇计划资本按天统计及预估表）
     * @param hjhPlanCapital
     * @return
     */
    public boolean updateHjhPlanCapital(HjhPlanCapitalVO hjhPlanCapital) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("date").is(hjhPlanCapital.getDate());
        criteria.and("planNid").is(hjhPlanCapital.getPlanNid());

        Update update = new Update();
        update.inc("updateTime", GetDate.getNowTime10()).set("delFlg", 0);

        this.update(query, update);
        return true;
    }

    /**
     * 插入记录（汇计划资本按天统计及预估表）
     * @param hjhPlanCapital
     * @return
     */
    public boolean insertHjhPlanCapital(HjhPlanCapitalVO hjhPlanCapital) {
        hjhPlanCapital.setCreateTime(GetDate.getNowTime10());
        hjhPlanCapital.setDelFlg(0);
        this.insert(hjhPlanCapital);
        return true;
    }
}
