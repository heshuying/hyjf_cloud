/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.OperationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version OperationalDataServiceImpl, v0.1 2018/12/18 17:13
 */
@Service
public class OperationalDataServiceImpl implements OperationalDataService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void countRechargeMoney() {
        amTradeClient.countRechargeMoney();
    }
}
