package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CoupUserResponse;
import com.hyjf.am.response.trade.HjhDebtCreditRepayResponse;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.cs.trade.client.HjhDebtCreditRepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 汇计划债转还款记录
 * @author hesy
 * @version HjhDebtCreditRepayClientImpl, v0.1 2018/7/4 19:05
 */
public class HjhDebtCreditRepayClientImpl implements HjhDebtCreditRepayClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询相应的汇计划债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    @Override
    public List<HjhDebtCreditRepayVO> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        String url = "http://AM-TRADE/am-trade/get/" + borrowNid + "/" + tenderOrderId + "/" + periodNow + "/" + status;
        HjhDebtCreditRepayResponse response = restTemplate.getForEntity(url, HjhDebtCreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
