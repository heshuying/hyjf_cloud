package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ProjectDetailResponse;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.cs.trade.client.AmProjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AmProjectClientImpl implements AmProjectClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProjectCustomeDetailVO selectProjectDetail(String borrowNid) {
        ProjectDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectDetail/" + borrowNid, ProjectDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }
}
