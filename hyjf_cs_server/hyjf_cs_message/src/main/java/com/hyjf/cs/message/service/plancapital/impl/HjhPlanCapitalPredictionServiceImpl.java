/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.plancapital.impl;

import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalPrediction;
import com.hyjf.cs.message.mongo.ic.HjhPlanCapitalPredictionDao;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalPredictionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资金计划-预计新增复投/债转额相关mongo操作
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalPredictionServiceImpl, v0.1 2019/4/16 14:21
 */
@Service
public class HjhPlanCapitalPredictionServiceImpl extends BaseServiceImpl implements HjhPlanCapitalPredictionService {

    @Autowired
    private HjhPlanCapitalPredictionDao planCapitalPredictionDao;

    /**
     * 预估结果插入mongo
     *
     * @param list
     * @return
     */
    @Override
    public boolean insertPlanCaptialPrediction(List<HjhPlanCapitalPredictionVO> list) {
        // 历史数据delflg为0的更新成1
        HjhPlanCapitalPredictionVO vo = new HjhPlanCapitalPredictionVO();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("delFlg").is(0);
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("delFlg", 1);
        this.planCapitalPredictionDao.updateAll(query, update);
        // 插入新增数据
        List<HjhPlanCapitalPrediction> inList = CommonUtils.convertBeanList(list, HjhPlanCapitalPrediction.class);
        this.planCapitalPredictionDao.insertAll(inList);
        return true;
    }

    /**
     * 预估结果查询总件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer getPlanCapitalPredictionCount(HjhPlanCapitalPredictionRequest request) {
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);

        return planCapitalPredictionDao.count(query).intValue();
    }

    /**
     * 预估结果列表
     *
     * @param request
     * @return
     */
    @Override
    public List<HjhPlanCapitalPrediction> getPlanCapitalPredictionList(HjhPlanCapitalPredictionRequest request) {
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);
        // 分页
        if (request.getLimitStart() != -1) {
            query.skip(request.getLimitStart()).limit(request.getLimitEnd());
        }

        List<HjhPlanCapitalPrediction> hjhPlanCapitalList = planCapitalPredictionDao.find(query);
        return hjhPlanCapitalList;
    }


    private Criteria createCriteria2(HjhPlanCapitalPredictionRequest request) {

        Criteria criteria;
        if (null != request) {
            criteria = Criteria.where("id").ne("").ne(null).and("delFlg").is(0);

            // 日期区间查询
            if (StringUtils.isNotBlank(request.getDateFromSrch()) && StringUtils.isNotBlank(request.getDateToSrch())) {
                criteria = criteria.and("date").gte(GetDate.stringToDate(request.getDateFromSrch() + " 00:00:00")).
                        lte(GetDate.stringToDate(request.getDateToSrch() + " 23:59:59"));
            }

            // 计划编号查询
            if (StringUtils.isNotBlank(request.getPlanNidSrch())) {
                criteria = criteria.and("planNid").is(request.getPlanNidSrch());
            }

            // 计划名称查询
            if (StringUtils.isNotBlank(request.getPlanNameSrch())) {
                criteria = criteria.and("planName").is(request.getPlanNameSrch());
            }

            // 锁定期查询
            if (StringUtils.isNotEmpty(request.getLockPeriodSrch())) {
                criteria = criteria.and("lockPeriod").is(Integer.valueOf(request.getLockPeriodSrch()));
            }

            return criteria;
        }
        return new Criteria();
    }
}
