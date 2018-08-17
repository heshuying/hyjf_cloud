package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;

/**
 * 汇计划 - 资金计划 (已迁移)
 */
public interface HjhPlanCapitalClient {

    /**
     * 获取汇计划--计划资金列表
     */
    HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);
}
