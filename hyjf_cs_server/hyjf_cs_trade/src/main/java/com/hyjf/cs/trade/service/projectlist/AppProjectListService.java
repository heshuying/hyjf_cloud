package com.hyjf.cs.trade.service.projectlist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.app.AppProjectInvestBeanRequest;

import com.hyjf.am.resquest.trade.ProjectListRequest;

import com.hyjf.cs.trade.bean.HjhPlanAccedeResultBean;
import com.hyjf.cs.trade.bean.HjhPlanBorrowResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动项目列表Service
 *
 * @author zhangyk
 *
 */
public interface AppProjectListService extends BaseTradeService {

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
    public JSONObject getAppProjectDetail(String borrowNid, HttpServletRequest request, Integer userId);


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
    public JSONObject getAppCreditDetail(String creditNid, Integer userId);

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
    public JSONObject getAppPlanDetail(String planNid, Integer userId);

    /**
     * 散标投资记录列表
     * @param info
     * @param form
     */
    void createProjectInvestPage(JSONObject info, AppProjectInvestBeanRequest form);

    /**
     * 创建计划的标的组成分页信息
     * @param result
     * @param planId
     * @param pageNo
     * @param pageSize
     */
    void searchHjhPlanBorrow(HjhPlanBorrowResultBean result, String planId, int pageNo, int pageSize);

    /**
     * app 端汇计划加入记录
     * @param result
     * @param planId
     * @param currentPage
     * @param pageSize
     */
    void getHjhPlanAccede(HjhPlanAccedeResultBean result, String planId, int currentPage, int pageSize);

    /**
     * app端债转承接记录
     * @param transferId
     * @param currentPage
     * @param pageSize
     * @return
     */
    BaseResultBeanFrontEnd investRecord(String transferId, Integer currentPage, Integer pageSize);


    /**
     * 承接记录列表
     * @author zhangyk
     * @date 2018/8/9 10:39
     */
    JSONObject searchProjectUndertakeList(String borrowNid,HttpServletRequest request);
}
