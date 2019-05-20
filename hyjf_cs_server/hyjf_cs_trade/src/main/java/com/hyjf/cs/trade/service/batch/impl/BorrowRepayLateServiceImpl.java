/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.service.batch.BorrowRepayLateService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version BorrowRepayLateServiceImpl, v0.1 2019/3/20 13:57
 */
@Service
public class BorrowRepayLateServiceImpl extends BaseTradeServiceImpl implements BorrowRepayLateService {
    @Override
    public void updateBorrowRepayLateInfo() {
        amTradeClient.updateBorrowRepayLateInfo();
    }
}
