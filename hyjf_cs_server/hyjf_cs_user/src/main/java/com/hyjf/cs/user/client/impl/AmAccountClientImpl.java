package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.user.client.AmAccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmAccountClientImpl implements AmAccountClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        AccountResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId ,AccountResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


}
