package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.HjhAutoEndCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/12/18.
 */
@Service
public class HjhAutoEndCreditServiceImpl implements HjhAutoEndCreditService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 汇计划自动结束转让定时任务
     * @return
     */
    @Override
    public void hjhAutoEndCredit(){
        amTradeClient.hjhAutoEndCredit();
    }

}
