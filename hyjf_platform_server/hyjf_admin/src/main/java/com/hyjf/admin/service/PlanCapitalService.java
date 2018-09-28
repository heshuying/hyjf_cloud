package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;

/**
 * 汇计划 -- 资金计划 Service
 * @Author : huanghui
 */
public interface PlanCapitalService {

    /**
     * 获取资金计划列表
     * @param hjhPlanCapitalRequest
     * @return
     */
    HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);
}
