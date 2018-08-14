package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CreditRepayResponse;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.cs.trade.client.CreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CreditClientImpl implements CreditClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CreditRepayVO> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status) {
        String url = "http://AM-TRADE/am-trade/creditTender/select_credit_repay_list/" + borrowNid + "/" + tenderOrderId + "/" + periodNow + "/" + status;
        CreditRepayResponse response = restTemplate.getForEntity(url, CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

}
