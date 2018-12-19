package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.HjhAutoCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/12/18.
 */
@Service
public class HjhAutoCreditServiceImpl implements HjhAutoCreditService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 汇计划自动清算
     * @return
     */
    @Override
    public void hjhAutoCredit(){
        amTradeClient.hjhAutoCredit();
    }

}
