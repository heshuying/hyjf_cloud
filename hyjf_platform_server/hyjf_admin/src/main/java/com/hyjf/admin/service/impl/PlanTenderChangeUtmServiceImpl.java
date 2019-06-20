/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.PlanTenderChangeUtmService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.TenderUpdateUtmHistoryResponse;
import com.hyjf.am.resquest.trade.UpdateTenderUtmExtRequest;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cui
 * @version PlanTenderChangeUtmServiceImpl, v0.1 2019/6/18 17:42
 */
@Service
public class PlanTenderChangeUtmServiceImpl implements PlanTenderChangeUtmService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public HjhPlanAccedeCustomizeVO getPlanTenderInfo(String planOrderId) {

        return amTradeClient.getPlanTenderInfo(planOrderId);

    }

    @Override
    public AdminResult updateTenderUtm(UpdateTenderUtmExtRequest updateTenderUtmRequest) {
        IntegerResponse response=amTradeClient.updatePlanTenderUtm(updateTenderUtmRequest);
        if(response.getResultInt()!=null && response.getResultInt() >0){
            return new AdminResult();
        }else{
            return new AdminResult(BaseResult.FAIL);
        }
    }

    @Override
    public TenderUpdateUtmHistoryResponse getPlanTenderChangeLog(String nid) {
        return amTradeClient.getPlanTenderUtmChangeLog(nid);
    }
}
