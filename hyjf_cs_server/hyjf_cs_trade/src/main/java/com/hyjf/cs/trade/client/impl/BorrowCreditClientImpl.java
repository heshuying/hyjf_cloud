/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowCreditResponse;
import com.hyjf.am.response.trade.BorrowRepayResponse;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.cs.trade.client.BorrowCreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditClientImpl, v0.1 2018/6/24 9:25
 */
@Service
public class BorrowCreditClientImpl implements BorrowCreditClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowCreditVO> selectBorrowCreditList() {
        BorrowCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowCredit/selectBorrowCreditList/",
                BorrowCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowCredit/updateBorrowCredit/", borrowCreditVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }
}
