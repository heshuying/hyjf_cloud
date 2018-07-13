package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowLogClient;
import com.hyjf.am.response.admin.AdminBorrowLogResponse;
import com.hyjf.am.response.admin.AdminBorrowRecoverResponse;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogClientImpl, v0.1 2018/7/11 10:30
 */
@Service
public class BorrowLogClientImpl implements BorrowLogClient {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Integer countBorrowLog(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/countBorrowLog";
        AdminBorrowLogResponse response = restTemplate.postForEntity(url,request,AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowLogCustomizeVO> selectBorrowLogList(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/selectBorrowLogList";
        AdminBorrowLogResponse response =restTemplate.postForEntity(url,request,AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/exportBorrowLogList";
        AdminBorrowLogResponse response =restTemplate.postForEntity(url,request,AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
