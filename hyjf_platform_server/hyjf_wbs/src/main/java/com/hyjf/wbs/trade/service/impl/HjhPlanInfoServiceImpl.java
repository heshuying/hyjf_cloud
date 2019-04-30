package com.hyjf.wbs.trade.service.impl;

import com.hyjf.wbs.trade.dao.model.auto.HjhPlan;
import com.hyjf.wbs.trade.dao.model.auto.HjhPlanExample;
import com.hyjf.wbs.trade.service.HjhPlanInfoService;
import com.hyjf.wbs.user.dao.model.auto.BankCardExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wxd
 * @Date: 2019-04-30 13:56
 * @Description:
 */
@Service
public class HjhPlanInfoServiceImpl extends BaseServiceImpl implements HjhPlanInfoService {
    @Override
    public HjhPlan selectHjhPlanInfo(String nid) {
        HjhPlanExample hjhPlanExample = new HjhPlanExample();
        HjhPlanExample.Criteria criteria =  hjhPlanExample.createCriteria();
        criteria.andPlanNidEqualTo(nid);
        List<HjhPlan> hjhPlans = hjhPlanMapper.selectByExample(hjhPlanExample);
        if(hjhPlans!=null && hjhPlans.size()>0){
            return hjhPlans.get(0);
        }
        return null;
    }
}
