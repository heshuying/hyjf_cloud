package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowUserResponse;
import com.hyjf.am.response.trade.ProjectCompanyResponse;
import com.hyjf.am.response.trade.ProjectPersonDetailResponse;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.cs.trade.client.BorrowUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BorrowUserClientImpl implements BorrowUserClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowUserVO getBorrowUser(String borrowNid) {
        BorrowUserResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowuser/borrowuserinfo/" + borrowNid,
                BorrowUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ProjectCompanyDetailVO searchProjectCompanyDetail(String borrowNid) {
        ProjectCompanyResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectCompany/" + borrowNid, ProjectCompanyResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebProjectPersonDetailVO searchProjectPersonDetail(String borrowNid) {
        ProjectPersonDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectPserson/" + borrowNid,ProjectPersonDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


}
