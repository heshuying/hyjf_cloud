package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRepaymentInfoClient;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoResponse;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoClientImpl, v0.1 2018/7/9 9:23
 */
@Service
public class BorrowRepaymentInfoClientImpl implements BorrowRepaymentInfoClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowRepaymentInfo(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/countBorrowRepaymentInfo";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/selectBorrowRepaymentInfoListForView";
        AdminBorrowRepaymentInfoResponse response =restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRepaymentInfoCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/sumBorrowRepaymentInfo";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/selectBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoResponse response =restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
