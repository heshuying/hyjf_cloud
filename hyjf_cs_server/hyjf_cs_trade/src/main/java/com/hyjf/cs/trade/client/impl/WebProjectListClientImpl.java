package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectDetailResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.cs.trade.client.WebProjectListClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WebProjectListClientImpl implements WebProjectListClient {

    private static Logger logger = LoggerFactory.getLogger(WebProjectListClientImpl.class);

    public static final  String BASE_URL = "http://AM-TRADE/am-trade/projectlist";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request) {
       ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectList",request,ProjectListResponse.class).getBody();
       logger.info("WebProjectListClientImpl --> searchProjectList --> response = {}",response);
       if (Response.isSuccess(response)){
           return response.getResultList();
       }
       return null;
    }

    @Override
    public Integer countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    @Override
    public ProjectCustomeDetailVO searchProjectDetail(Map map) {
        ProjectDetailResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectDetail",map,ProjectDetailResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
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

    /**
     * web:查询计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:27
     */
    @Override
    public Map<String, Object> searchPlanData(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/planData",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getTotalData();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据count
     * @author zhangyk
     * @date 2018/6/21 15:28
     */
    @Override
    public Integer countPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/planData",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    @Override
    public List<WebProjectListCustomizeVO> searchPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchPlanList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
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
    public ProjectListResponse searchAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    public Integer countAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }


    /**
     * APP端查询计划数据list
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    public List<WebProjectListCustomizeVO> searchAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countAppProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    /******************************  app end **************************************/
}
