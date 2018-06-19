package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.trade.client.WebProjectListClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebProjectListClientImpl implements WebProjectListClient {

    private static Logger logger = LoggerFactory.getLogger(WebProjectListClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ProjectListResponse searchProjectList(ProjectListRequest request) {
       ProjectListResponse response =  restTemplate.postForEntity("http://AM-TRADE/web/projectlist/searchProjectList",request,ProjectListResponse.class).getBody();
       logger.info("WebProjectListClientImpl --> searchProjectList --> response = {}",response);
       return response;
    }

    @Override
    public ProjectListResponse countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity("http://AM-TRADE/web/projectlist/countProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse countCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity("http://AM-TRADE/web/projectlist/countCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countCreditList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse searchCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity("http://AM-TRADE/web/projectlist/searchCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchCreditList --> response = {}",response);
        return response;
    }
}
