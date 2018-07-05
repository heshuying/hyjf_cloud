package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.cs.trade.client.BorrowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version BorrowClientImpl, v0.1 2018/6/19 15:33
 */
@Service
public class BorrowClientImpl implements BorrowClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid,
                BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
