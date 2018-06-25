package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.cs.trade.client.AmBorrowUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmBorrowUserClientImpl implements AmBorrowUserClient {



    @Autowired
    private RestTemplate restTemplate;

    public BorrowUserVO getBorrowUser(String borrowNid){
        return null;
    }
}
