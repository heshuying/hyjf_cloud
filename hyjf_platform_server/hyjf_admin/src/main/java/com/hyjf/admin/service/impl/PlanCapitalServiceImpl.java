package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.am.response.admin.HjhPlanCapitalActualResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalPredictionResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalPredictionRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资金计划 实现
 * @Author : huanghui
 */
@Service
public class PlanCapitalServiceImpl implements PlanCapitalService {


    @Autowired
    private CsMessageClient csMessageClient;

    @Override
    public HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest) {
        HjhPlanCapitalResponse planCapitalResponse = this.csMessageClient.getPlanCapitalList(hjhPlanCapitalRequest);
        return planCapitalResponse;
    }

    @Override
    public HjhPlanCapitalPredictionResponse getPlanCapitalPredictionList(HjhPlanCapitalPredictionRequest hjhPlanCapitalPredictionRequest) {
        HjhPlanCapitalPredictionResponse hjhPlanCapitalPredictionResponse = this.csMessageClient.getPlanCapitalPredictionList(hjhPlanCapitalPredictionRequest);
        return hjhPlanCapitalPredictionResponse;
    }

    /**
     * 获取资金计划3.3.0列表（实际）
     * @param hjhPlanCapitalActualRequest
     * @return
     */
    @Override
    public HjhPlanCapitalActualResponse getPlanCapitalActualList(HjhPlanCapitalActualRequest hjhPlanCapitalActualRequest){
        HjhPlanCapitalActualResponse hjhPlanCapitalActualResponse = this.csMessageClient.getPlanCapitalActualInfo(hjhPlanCapitalActualRequest);
        return hjhPlanCapitalActualResponse;
    }
}

