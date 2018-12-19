/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.HjhPlanJoinSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version HjhPlanJoinSwitchServiceImpl, v0.1 2018/12/18 15:43
 */
@Service
public class HjhPlanJoinSwitchServiceImpl implements HjhPlanJoinSwitchService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public BooleanResponse hjhPlanJoinOff() {
        return amTradeClient.updateHjhPlanJoinOff();
    }

    @Override
    public BooleanResponse hjhPlanJoinOn() {
        return amTradeClient.updateHjhPlanJoinOn();
    }
}
