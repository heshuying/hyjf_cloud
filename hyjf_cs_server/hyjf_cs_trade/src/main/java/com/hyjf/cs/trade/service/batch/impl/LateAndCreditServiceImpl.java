/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.LateAndCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version LateAndCreditServiceImpl, v0.1 2018/12/18 17:02
 */
@Service
public class LateAndCreditServiceImpl implements LateAndCreditService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void updateRepayInfo() {
        amTradeClient.updateRepayInfo();
    }
}
