package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
@Repository
public class HjhPlanCapitalDao extends BaseMongoDao<HjhPlanCapital> {

    @Override
    protected Class<HjhPlanCapital> getEntityClass(){
        return HjhPlanCapital.class;
    }
//    protected Class<HjhPlanCapitalVO> getEntityClass() {
//        return HjhPlanCapitalVO.class;
//    }

    /**
     * 查询类表条数
     * @param request
     * @return
     */
    public Integer getCount(HjhPlanCapitalRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);

        return (int)mongoTemplate.count(query, getEntityClass());
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    public List<HjhPlanCapital> findAllList(HjhPlanCapitalRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);
        // 分页
        if(request.getLimitStart()!=-1){
            query.skip(request.getLimitStart()-1).limit(request.getLimitEnd());
        }
        List<HjhPlanCapital> hjhPlanCapitalList = mongoTemplate.find(query, getEntityClass());
        return hjhPlanCapitalList;
    }

    private Criteria createCriteria2(HjhPlanCapitalRequest request){

        Criteria criteria;
        if(null!=request){
            criteria = Criteria.where("id").ne("").ne(null);

            // 日期区间查询
            if (StringUtils.isNoneBlank(request.getDateFromSrch()) && StringUtils.isNotBlank(request.getDateToSrch())){
                criteria = criteria.and("date").gte(request.getDateFromSrch()).lte(request.getDateToSrch());
            }

            // 计划编号查询
            if (StringUtils.isNotBlank(request.getPlanNidSrch())){
                criteria = criteria.and("planNid").is(request.getPlanNidSrch());
            }

            // 计划名称查询
            if (StringUtils.isNotBlank(request.getPlanNameSrch())){
                criteria = criteria.and("planName").is(request.getPlanNameSrch());
            }

            // 锁定期查询
            if (StringUtils.isNotEmpty(request.getLockPeriodSrch())){
                criteria = criteria.and("lockPeriod").is(Integer.valueOf(request.getLockPeriodSrch()));
            }

            return criteria;
        }
        return new Criteria();
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
//        return this.find(query);
        return null;
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
//        this.insert(hjhPlanCapital);
        return true;
    }
}
