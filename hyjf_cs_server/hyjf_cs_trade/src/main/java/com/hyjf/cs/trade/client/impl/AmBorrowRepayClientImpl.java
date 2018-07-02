/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowRepayResponse;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.cs.trade.client.AmBorrowRepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AmBorrowRepayClientImpl, v0.1 2018/6/22 19:06
 */
@Service
public class AmBorrowRepayClientImpl implements AmBorrowRepayClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/selectBorrowRepayList/" + borrowNid + "/" + repaySmsReminder,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/updateBorrowRepay/",borrowRepayVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }



    @Override
    public List<BorrowRepayVO> getBorrowRepayList(String borrowNid) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/getBorrowRepayListByBorrowNid/" + borrowNid  ,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


}
