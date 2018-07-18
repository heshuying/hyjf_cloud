package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowCreditDetailResponse;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.cs.trade.client.AmBorrowCreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmBorrowCreditClientImpl implements AmBorrowCreditClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowCreditDetailVO getCreditDetail(String creditNid) {
        BorrowCreditDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrowCredit/borrowCreditDetail/" + creditNid,BorrowCreditDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
           return response.getResult();
        }
        return null;
    }
}
