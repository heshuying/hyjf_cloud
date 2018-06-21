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

    public static final  String BASE_URL = "http://AM-TRADE/am-trade/projectlist";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ProjectListResponse searchProjectList(ProjectListRequest request) {
       ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectList",request,ProjectListResponse.class).getBody();
       logger.info("WebProjectListClientImpl --> searchProjectList --> response = {}",response);
       return response;
    }

    @Override
    public ProjectListResponse countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse countCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countCreditList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse searchCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchCreditList --> response = {}",response);
        return response;
    }



    /*******************************  web end *************************************/
    /******************************  app start **************************************/
    /**
     *  app端获取散标投资项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    @Override
    public ProjectListResponse countAppProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppProjectList --> response = {}",response);
        return response;
    }

    /**
     * app端获取散标投资项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    @Override
    public ProjectListResponse searchAppProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     *  app端查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */

    @Override
    public ProjectListResponse countAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppCreditList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    @Override
    public ProjectListResponse searchCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }


    /******************************  app end **************************************/
}
