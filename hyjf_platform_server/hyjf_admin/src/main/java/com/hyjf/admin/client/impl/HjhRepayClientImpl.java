package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhRepayClient;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 产品中心 -> 汇计划 -> 订单退出
 * @Author : huanghui
 */
@Service
public class HjhRepayClientImpl implements HjhRepayClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer getRepayCount(HjhRepayRequest repayRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhRepay/getRepayCount", repayRequest, Integer.class).getBody();
    }

    @Override
    public List<HjhRepayVO> selectByExample(HjhRepayRequest request) {
        HjhRepayResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhRepay/hjhRepayList", request, HjhRepayResponse.class).getBody();

        if (response != null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 通过 accedeOrderId 精确检索还款明细
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId) {
        HjhRepayResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhRepay/hjhRepaymentDetails/" + accedeOrderId, HjhRepayResponse.class).getBody();
        if (response != null){
            return response.getResultList();
        }
        return null;
    }
}
