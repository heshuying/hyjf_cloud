/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.ic;

import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalPrediction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author wenxin
 * @version HjhPlanCapitalPredictionDao, v0.1 2019/4/2 15:51
 */
public class HjhPlanCapitalPredictionDao extends BaseMongoDao<HjhPlanCapitalPrediction>  {

    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    @Override
    protected Class<HjhPlanCapitalPrediction> getEntityClass(){
        return HjhPlanCapitalPrediction.class;
    }

    /**
     * 查询类表条数
     * @param request
     * @return
     */
    public Integer getCount(HjhPlanCapitalPredictionRequest request){
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
    public List<HjhPlanCapitalPrediction> findAllList(HjhPlanCapitalPredictionRequest request){
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);
        // 分页
        if(request.getLimitStart()!=-1){
            query.skip(request.getLimitStart()).limit(request.getLimitEnd());
        }

        logger.info("汇计划->资金计划列表和导出列表查询条件:" + request.getDateFromSrch() + ":" + request.getDateToSrch() + ":" + query.toString());
        List<HjhPlanCapitalPrediction> list = mongoTemplate.find(query, getEntityClass());
        return list;
    }

    private Criteria createCriteria2(HjhPlanCapitalPredictionRequest request){

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
    public boolean updateHjhPlanPrediction(HjhPlanCapitalPredictionVO vo) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("date").is(vo.getDate());
        criteria.and("planNid").is(vo.getPlanNid());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("addCreditAccount", vo.getCreditAccount())  //预计当日新增债转额
                .set("createCreditAccount", vo.getReinvestAccount()) //预计当日新增复投额
                .set("usedCreditAccount", vo.getCapitalAccount()) //预计当日所需资金量:预计当日新增债转额（元）- 预计当日新增债转额（元）（若为负数显示为0）
                .set("leaveCreditAccount", vo.getAssetAccount()) //预计当日所需资产量:预计当日新增债转额（元）-预计当日新增债转额（元）（若为负数显示为0）
                .set("updateTime", GetDate.getDate()).set("delFlg", 0);
        this.update(query, update);
        return true;
    }

    /**
     * 插入记录（汇计划资本按天统计及预估表）
     * @param vo
     * @return
     */
    public boolean insertHjhPlanPrediction(HjhPlanCapitalPredictionVO vo) {
        HjhPlanCapitalPrediction re = CommonUtils.convertBean(vo, getEntityClass());
        re.setCreateTime(GetDate.getDate());
        re.setDelFlg(0);
        this.insert(re);
        return true;
    }
}
