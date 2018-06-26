/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.trade.client.BorrowRepayToHjhQuitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitClientImpl, v0.1 2018/6/25 9:41
 */
@Service
public class BorrowRepayToHjhQuitClientImpl implements BorrowRepayToHjhQuitClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<HjhAccedeVO> selectWaitQuitHjhList() {
        HjhAccedeResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhAccede/selectWaitQuitHjhList/",
                HjhAccedeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
