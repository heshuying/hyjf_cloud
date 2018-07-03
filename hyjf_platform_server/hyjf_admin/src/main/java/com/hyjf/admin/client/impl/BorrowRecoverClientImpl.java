package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRecoverClient;
import com.hyjf.am.response.admin.AdminBorrowRecoverResponse;
import com.hyjf.am.response.trade.TenderDetailResponse;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverClientImpl, v0.1 2018/7/2 10:18
 */
@Service
public class BorrowRecoverClientImpl implements BorrowRecoverClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowRecover(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/countBorrowRecover";
        AdminBorrowRecoverResponse response = restTemplate.postForEntity(url,request,AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowRecoverCustomizeVO> selectBorrowRecoverList(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/selectBorrowRecoverList";
        AdminBorrowRecoverResponse response =restTemplate.postForEntity(url,request,AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRecoverCustomizeVO sumBorrowRecoverList(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/sumBorrowRecoverList";
        AdminBorrowRecoverResponse response = restTemplate.postForEntity(url,request,AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
