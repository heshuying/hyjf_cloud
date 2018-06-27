package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.WebCarinfoResponse;
import com.hyjf.am.vo.trade.WebCarinfoVO;
import com.hyjf.cs.trade.client.AmBorrowCarinfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AmBorrowCarinfoClientImpl implements AmBorrowCarinfoClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<WebCarinfoVO> getBorrwCarinfo(String borrowNid) {
        WebCarinfoResponse response = restTemplate.getForEntity("",WebCarinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
