package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.PoundageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/12/18.
 */
@Service
public class PoundageServiceImpl implements PoundageService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 手续费分账明细插入定时
     * @return
     */
    @Override
    public void poundage(){
        amTradeClient.poundage();
    }

}
