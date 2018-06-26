package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowManinfoResponse;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.cs.trade.client.AmBorrowManinfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmBorrowManinfoClientImpl implements AmBorrowManinfoClient {


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public BorrowManinfoVO getBorrowManinfo(String borrowNid) {
        BorrowManinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowmaninfo/" + borrowNid ,BorrowManinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
}
