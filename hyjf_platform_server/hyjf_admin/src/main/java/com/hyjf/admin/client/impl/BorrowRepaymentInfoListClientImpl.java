package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRepaymentInfoListClient;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoListResponse;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListClientImpl, v0.1 2018/7/10 10:46
 */
@Service
public class BorrowRepaymentInfoListClientImpl implements BorrowRepaymentInfoListClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Integer countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/countBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoListResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowRepaymentInfoListCustomizeVO> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/selectBorrowRepaymentInfoListList";
        AdminBorrowRepaymentInfoListResponse response =restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRepaymentInfoListCustomizeVO sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/sumBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoListResponse response = restTemplate.postForEntity(url,request,AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
