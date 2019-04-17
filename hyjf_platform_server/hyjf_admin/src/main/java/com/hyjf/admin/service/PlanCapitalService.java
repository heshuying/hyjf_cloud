package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhPlanCapitalPredictionResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
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

    /**
     * 获取资金计划3.3.0列表（预计）
     * @param hjhPlanCapitalPredictionRequest
     * @return
     */
    HjhPlanCapitalPredictionResponse getPlanCapitalPredictionList(HjhPlanCapitalPredictionRequest hjhPlanCapitalPredictionRequest);
}
