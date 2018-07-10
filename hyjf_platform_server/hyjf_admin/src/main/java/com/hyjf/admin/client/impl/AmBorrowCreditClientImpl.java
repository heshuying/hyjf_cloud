package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.client.AmBorrowCreditClient;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.admin.AdminBorrowCreditResponse;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AmBorrowCreditClientImpl implements AmBorrowCreditClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditList4admin",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer getBorrowCreditCount(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/countBorrowCreditList4admin",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getRecordTotal();
        }
        return null;
    }

    @Override
    public BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditTotalCount",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getSumVO();
        }
        return null;
    }


}
