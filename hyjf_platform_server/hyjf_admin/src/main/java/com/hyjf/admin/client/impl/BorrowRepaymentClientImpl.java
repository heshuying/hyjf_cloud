package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRepaymentClient;
import com.hyjf.am.response.admin.AdminBorrowRecoverResponse;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentClientImpl, v0.1 2018/7/4 12:00
 */
@Service
public class BorrowRepaymentClientImpl implements BorrowRepaymentClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowRepayment(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/countBorrowRepayment";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/selectBorrowRepaymentList";
        AdminBorrowRepaymentResponse response =restTemplate.postForEntity(url,request,AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRepaymentCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/sumBorrowRepaymentInfo";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/exportRepayClkActBorrowRepaymentInfoList";
        AdminBorrowRepaymentResponse response =restTemplate.postForEntity(url,request,AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getBorrowRepaymentPlanList();
        }
        return null;
    }
}
