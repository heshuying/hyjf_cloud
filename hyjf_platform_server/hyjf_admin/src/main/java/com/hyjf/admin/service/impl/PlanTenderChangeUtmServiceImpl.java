/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.PlanTenderChangeUtmService;
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

}
