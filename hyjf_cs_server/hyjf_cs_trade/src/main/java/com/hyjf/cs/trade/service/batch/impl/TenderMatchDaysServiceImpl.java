/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.TenderMatchDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version TenderMatchDaysServiceImpl, v0.1 2018/12/18 16:34
 */
@Service
public class TenderMatchDaysServiceImpl implements TenderMatchDaysService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public BooleanResponse tenderMatchDays() {
        return amTradeClient.updateMatchDays();
    }
}
