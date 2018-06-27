package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowHousesResponse;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.cs.trade.client.BorrowHousesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BorrowHousesClientImpl implements BorrowHousesClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid) {
        BorrowHousesResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowhouses/" + borrowNid, BorrowHousesResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }
}
