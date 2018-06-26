package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.client.AmAccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmAccountClientImpl implements AmAccountClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        return null;
    }
}
