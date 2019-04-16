/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalActual;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * 资金计划-当日实际金额相关
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualDao, v0.1 2019/4/2 14:26
 */
public class HjhPlanCapitalActualDao extends BaseMongoDao<HjhPlanCapitalActual>  {

    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    @Override
    protected Class<HjhPlanCapitalActual> getEntityClass(){
        return HjhPlanCapitalActual.class;
    }

    /**
     * 查询类表条数
     * @param request
     * @return
     */
    public Integer getCount(HjhPlanCapitalActualRequest request){
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
    public List<HjhPlanCapitalActual> findAllList(HjhPlanCapitalActualRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);
        // 分页
        if(request.getLimitStart()!=-1){
            query.skip(request.getLimitStart()).limit(request.getLimitEnd());
        }

        logger.info("汇计划->资金计划列表和导出列表查询条件:" + request.getDateFromSrch() + ":" + request.getDateToSrch() + ":" + query.toString());
        List<HjhPlanCapitalActual> list = mongoTemplate.find(query, getEntityClass());
        return list;
    }

    private Criteria createCriteria2(HjhPlanCapitalActualRequest request){

        Criteria criteria;
        if(null!=request){
            criteria = Criteria.where("id").ne("").ne(null);

            // 日期区间查询
            if (StringUtils.isNotBlank(request.getDateFromSrch()) && StringUtils.isNotBlank(request.getDateToSrch())){
                criteria = criteria.and("date").gte(GetDate.stringToDate(request.getDateFromSrch() + " 00:00:00")).
                        lte(GetDate.stringToDate(request.getDateToSrch() + " 23:59:59"));
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

    /**
     * 更新记录（汇计划资本按天统计）
     * @param vo
     * @return
     */
    public boolean updateHjhPlanCapital(HjhPlanCapitalActualVO vo) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("date").is(vo.getDate());
        criteria.and("planNid").is(vo.getPlanNid());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("addCreditAccount", vo.getAddCreditAccount())
                .set("createCreditAccount", vo.getCreateCreditAccount())
                .set("usedCreditAccount", vo.getUsedCreditAccount())
                .set("leaveCreditAccount", vo.getLeaveCreditAccount())
                .set("addReinvestAccount", vo.getAddReinvestAccount())
                .set("sumReinvestAccount", vo.getSumReinvestAccount())
                .set("usedReinvestAccount", vo.getUsedReinvestAccount())
                .set("leaveReinvestAccount", vo.getLeaveReinvestAccount())
                .set("updateTime", GetDate.getDate()).set("delFlg", 0);
        this.update(query, update);
        return true;
    }

    /**
     * 插入记录（汇计划资本按天统计及预估表）
     * @param vo
     * @return
     */
    public boolean insertHjhPlanCapital(HjhPlanCapitalActualVO vo) {
        HjhPlanCapitalActual re = CommonUtils.convertBean(vo, getEntityClass());
        re.setCreateTime(GetDate.getDate());
        re.setDelFlg(0);
        this.insert(re);
        return true;
    }
}
