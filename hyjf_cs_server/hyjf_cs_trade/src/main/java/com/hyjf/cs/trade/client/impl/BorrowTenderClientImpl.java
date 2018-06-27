package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.cs.trade.client.BorrowTenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BorrowTenderClientImpl implements BorrowTenderClient {



    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {
        BorrowTenderResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowtender/" +userId + "/" + borrowNid,BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
        }
        return null;
    }
}
