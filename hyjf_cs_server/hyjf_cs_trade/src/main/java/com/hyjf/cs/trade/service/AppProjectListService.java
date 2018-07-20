package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;

import java.util.Map;

/**
 * 移动项目列表Service
 *
 * @author zhangyk
 *
 */
public interface AppProjectListService extends BaseTradeService{

    /**
     * 获取移动端散标投资列表
     * @param request
     * @author zhangyk
     * @return
     */
    public JSONObject searchAppProjectList(ProjectListRequest request);


    /**
     * 获取移动端散标详情
     * @author zhangyk
     * @date 2018/6/28 16:15
     */
    public AppResult getAppProjectDetail(Map<String,String> param,String token);


    /**
     *  获取移动端债转列表
     * @author zhangyk
     * @date 2018/6/20 15:26
     */
    public JSONObject searchAppCreditList(ProjectListRequest request);

    /**
     * 获取移动端债转详情
     * @author zhangyk
     * @date 2018/6/30 10:40
     */
    public AppResult getAppCreditDetail(Map<String,String> param,String token);

    /**
     * 移动端计划列表
     * @author zhangyk
     * @date 2018/6/21 19:12
     */
    public JSONObject searchAppPlanList(ProjectListRequest request);

    /**
     * 移动端计划详情
     * @author zhangyk
     * @date 2018/6/29 16:27
     */
    public AppResult getAppPlanDetail(Map<String,String> param, String token);
}
