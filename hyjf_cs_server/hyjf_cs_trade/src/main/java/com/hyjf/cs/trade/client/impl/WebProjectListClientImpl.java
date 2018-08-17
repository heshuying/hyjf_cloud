package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.response.trade.HjhPlanResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.cs.trade.client.WebProjectListClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    /**
     * web:查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    @Override
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request) {
        HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchPlanList",request,HjhPlanResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
    @Override
    public PlanDetailCustomizeVO getPlanDetail(String planNid){
        HjhPlanDetailResponse response = restTemplate.getForEntity(BASE_URL + "/web/searchPlanDetail/" + planNid,HjhPlanDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /*******************************  web end *************************************/
}
