package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;

public interface HjhPlanCapitalClient {

    /**
     * 获取汇计划--计划资金列表
     */
    HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);
}
