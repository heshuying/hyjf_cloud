package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.AppProjectListResponse;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * Web端项目列表Client
 *
 * @author liuyang
 * @version WebProjectListClient, v0.1 2018/6/13 10:21
 */
public interface WebProjectListClient {

    /**
     * 获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request);

    /**
     * 查询所有分页总数
     * @param request
     * @return
     */
    public Integer countProjectList(ProjectListRequest request);

    /**
     * 获取标的详情
     * @author zhangyk
     * @date 2018/6/22 19:24
     */
    public ProjectCustomeDetailVO searchProjectDetail(Map map);

    /**
     *  查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse countCreditList(CreditListRequest request);

    /**
     *  查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse searchCreditList(CreditListRequest request);


    /**
     * 查询计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:27
     */
    public Map<String,Object>  searchPlanData(ProjectListRequest request);

    /**
     * 查询计划专区总数据count
     * @author zhangyk
     * @date 2018/6/21 15:28
     */
    public Integer  countPlanList(ProjectListRequest request);

    /**
     * 查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request);

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
    public PlanDetailCustomizeVO getPlanDetail(String planNid);

    // --------------------------- web end  -----------------------------------

    /* ************************  app start  **************************************/

    /**
     *  app端获取散标投资项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    public Integer countAppProjectList(AppProjectListRequest request);

    /**
     * app端获取散标投资项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    public List<AppProjectListCustomizeVO> searchAppProjectList(AppProjectListRequest request);

    /**
     *  app端查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public ProjectListResponse countAppCreditList(ProjectListRequest request);

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public ProjectListResponse searchAppCreditList(ProjectListRequest request);

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/21 19:17
     */
    public Integer countAppPlanList(ProjectListRequest request);

    /**
     * APP端查询计划数据list
     * @author zhangyk
     * @date 2018/6/21 19:17
     */
    public List<WebProjectListCustomizeVO> searchAppPlanList(ProjectListRequest request);
    /* ************************  app end  **************************************/

}
