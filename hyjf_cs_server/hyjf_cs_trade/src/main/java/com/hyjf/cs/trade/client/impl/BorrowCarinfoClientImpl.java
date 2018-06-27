package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowCarinfoResponse;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.cs.trade.client.BorrowCarinfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BorrowCarinfoClientImpl implements BorrowCarinfoClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid) {
        BorrowCarinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowcarinfo/" + borrowNid,BorrowCarinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
