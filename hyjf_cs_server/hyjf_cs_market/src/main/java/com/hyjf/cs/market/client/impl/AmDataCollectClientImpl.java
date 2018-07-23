/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.cs.market.client.AmDataCollectClient;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version AmDataCollectImpl, v0.1 2018/7/18 13:56
 */
@Service
public class AmDataCollectClientImpl implements AmDataCollectClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowUserStatisticVO selectBorrowUserStatistic() {
        //todo fuqiang
        return null;
    }

    @Override
    public String getTotalInvestmentAmount() {
        CalculateInvestInterestResponse response = restTemplate.getForObject(
                "http://CS-MESSAGE/cs-message/search/gettotalinvestmentamount",
                CalculateInvestInterestResponse.class);
        if (response != null) {
            BigDecimal interestSum = response.getInterestSum();
            if (interestSum != null) {
                return String.valueOf(interestSum.divide(new BigDecimal("100000000")));
            }
        }
        return null;
    }

}
