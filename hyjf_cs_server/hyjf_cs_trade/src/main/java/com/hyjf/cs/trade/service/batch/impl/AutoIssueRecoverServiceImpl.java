/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.AutoIssueRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version AutoIssueRecoverServiceImpl, v0.1 2018/12/18 16:19
 */
@Service
public class AutoIssueRecoverServiceImpl implements AutoIssueRecoverService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void autoIssueRecover() {
        amTradeClient.autoIssueRecover();
    }
}
