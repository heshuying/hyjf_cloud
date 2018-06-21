package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.cs.trade.client.SynBalanceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author pangchengchao
 * @version SynBalanceClientImpl, v0.1 2018/6/20 9:40
 */
@Service
public class SynBalanceClientImpl implements SynBalanceClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest) {
        String url = "http://AM-TRADE/am-trade/synBalance/insertAccountDetails";
        return restTemplate.postForEntity(url, synBalanceBeanRequest, Boolean.class).getBody();
    }
}
