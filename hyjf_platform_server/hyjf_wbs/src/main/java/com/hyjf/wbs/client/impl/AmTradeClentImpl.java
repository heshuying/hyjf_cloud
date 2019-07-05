/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.wbs.client.AmTradeClient;

/**
 * @author cui
 * @version AmTradeClentImpl, v0.1 2019/4/17 17:01
 */
@Service
public class AmTradeClentImpl implements AmTradeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.trade.service.name}")
    private String tradeService;

    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(tradeService+"/trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
