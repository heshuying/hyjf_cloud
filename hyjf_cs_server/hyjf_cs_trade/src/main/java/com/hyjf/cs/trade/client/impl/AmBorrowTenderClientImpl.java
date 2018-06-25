package com.hyjf.cs.trade.client.impl;

import com.hyjf.cs.trade.client.AmBorrowTenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmBorrowTenderClientImpl implements AmBorrowTenderClient {



    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {

        return null;
    }
}
