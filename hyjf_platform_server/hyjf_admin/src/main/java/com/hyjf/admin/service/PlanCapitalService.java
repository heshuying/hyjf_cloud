package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;

/**
 * 汇计划 -- 资金计划 Service
 * @Author : huanghui
 */
public interface PlanCapitalService {

    HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);

}
