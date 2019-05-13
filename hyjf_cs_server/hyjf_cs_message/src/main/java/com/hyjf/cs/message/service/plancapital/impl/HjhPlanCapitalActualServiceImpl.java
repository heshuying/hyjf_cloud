/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.plancapital.impl;

import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalActual;
import com.hyjf.cs.message.mongo.ic.HjhPlanCapitalActualDao;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalActualService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualServiceImpl, v0.1 2019/4/23 9:55
 */
@Service
public class HjhPlanCapitalActualServiceImpl extends BaseServiceImpl implements HjhPlanCapitalActualService {

    @Autowired
    private HjhPlanCapitalActualDao planCapitalActualDao;

    /**
     * 实际结果插入mongo
     *
     * @param list
     * @return
     */
    @Override
    public Boolean insertPlanCaptialActual(List<HjhPlanCapitalActualVO> list) {
        // 历史数据delflg为0的更新成1
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("delFlg").is(0);
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("delFlg", 1);
        this.planCapitalActualDao.updateAll(query, update);
        // 插入新增数据
        List<HjhPlanCapitalActual> inList = CommonUtils.convertBeanList(list, HjhPlanCapitalActual.class);
        this.planCapitalActualDao.insertAll(inList);
        return true;
    }

    /**
     * 实际结果查询总件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer getPlanCapitalActualCount(HjhPlanCapitalActualRequest request) {
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);

        return planCapitalActualDao.count(query).intValue();
    }

    /**
     * 实际结果查询列表
     *
     * @param request
     * @return
     */
    @Override
    public List<HjhPlanCapitalActual> getPlanCapitalActualList(HjhPlanCapitalActualRequest request) {
        Query query = new Query();
        Criteria criteria = createCriteria2(request);
        query.addCriteria(criteria);
        // 分页
        if (request.getLimitStart() != -1) {
            query.skip(request.getLimitStart()).limit(request.getLimitEnd());
        }

        List<HjhPlanCapitalActual> hjhPlanCapitalList = planCapitalActualDao.find(query);
        return hjhPlanCapitalList;
    }

    /**
     * 查询条件封装
     *
     * @param request
     * @return
     */
    private Criteria createCriteria2(HjhPlanCapitalActualRequest request) {

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
