package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.HjhAutoCalculateFairValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/12/18.
 */
@Service
public class HjhAutoCalculateFairValueServiceImpl implements HjhAutoCalculateFairValueService {

    @Autowired
    private AmTradeClient amTradeClient;


    /**
     * 汇计划自动计算计划订单公允价值
     * @return
     */
    @Override
    public void hjhCalculateFairValue(){
        amTradeClient.hjhCalculateFairValue();
    }


}
