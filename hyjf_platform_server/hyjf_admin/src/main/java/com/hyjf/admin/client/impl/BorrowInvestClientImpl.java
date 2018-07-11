/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowInvestClient;
import com.hyjf.am.response.admin.BorrowInvestCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestClientImpl, v0.1 2018/7/10 9:19
 */
@Service
public class BorrowInvestClientImpl implements BorrowInvestClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/count_borrow_invest";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_list";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_account";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getSumAccount();
        }
        return null;
    }
}
