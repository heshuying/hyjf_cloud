package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhDebtCreditClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:
 */
@Service
public class HjhDebtCreditClientImpl implements HjhDebtCreditClient{

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询汇计划转让列表
     * @param request
     * @return
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse response =  restTemplate.
                  postForEntity("http://AM-TRADE/am-trade/adminHjhDebtCredit/getList", request, HjhDebtCreditReponse.class).
                  getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public Integer queryHjhDebtCreditListTotal(HjhDebtCreditListRequest request) {

        Integer total =  restTemplate.
                postForEntity("http://AM-TRADE/am-trade/adminHjhDebtCredit/getListTotal", request, Integer.class).
                getBody();
        return total;
    }
}
