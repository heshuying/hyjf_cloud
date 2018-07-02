package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;

public interface AmHjhPlanClient {

    PlanDetailCustomizeVO getPlanDetailByPlanNid(String planId);
}
