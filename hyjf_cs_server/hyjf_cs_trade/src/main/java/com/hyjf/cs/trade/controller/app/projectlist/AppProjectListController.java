/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.projectlist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.app.AppProjectInvestBeanRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.bean.HjhPlanAccedeResultBean;
import com.hyjf.cs.trade.bean.HjhPlanBorrowResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.projectlist.AppProjectListService;
import com.hyjf.cs.trade.util.ProjectConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 移动端项目列表
 *
 * @author zhangyk
 */
@Api(tags = "APP端项目列表")
@RestController
@RequestMapping(ProjectConstant.REQUEST_HOME )
public class AppProjectListController extends BaseTradeController {

    @Autowired
    private AppProjectListService appProjectListService;

    /**
     * app端获取散标投资列表
     * 原接口：com.hyjf.app.project.projectController.searchProjectList()
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端散标列表", notes = "APP投资散标列表")
    @PostMapping(value ="/projectlist/borrowProjectList", produces = "application/json; charset=utf-8")
    public Object homeBorrowProjectList(@ModelAttribute @Valid ProjectListRequest request) {
        // controller 不做业务处理
        JSONObject result = appProjectListService.searchAppProjectList(request);
        return result;
    }

    /**
     * app端获取散标投资详情
     * 原接口：com.hyjf.app.project.BorrowProjectController.searchProjectDetail()
     * @param param
     * @return
     */
    @ApiOperation(value = "APP端散标详情", notes = "APP端散标详情")
    @ApiImplicitParam(name = "param", value = "{borrowId:string,borrowType:string<1是债转,0 普通>}", dataType = "Map")
    @GetMapping(value = "/borrow/{borrowId}", produces = "application/json; charset=utf-8")
    public Object borrowProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "token", required = false) String token) {
        // controller 不做业务处理
        JSONObject result = appProjectListService.getAppProjectDetail(borrowId,request, token);
        return result;
    }

    /**
     * app端获取散标投资记录
     * add by jijun 20180726
     * 原接口:com.hyjf.app.project.BorrowProjectController.searchProjectInvestList()
     */
    @ApiOperation(value = "APP端散标投资记录", notes = "APP端散标投资记录")
    @GetMapping(value = "/borrow/{borrowId}/investRecord", produces = "application/json; charset=utf-8")
    public JSONObject searchProjectInvestList(@PathVariable("borrowId") String borrowNid, HttpServletRequest request, HttpServletResponse response) {
        JSONObject info = new JSONObject();
        Integer currentPage = 1;
        if(request.getParameter("currentPage") != null){
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        Integer size = 10;
        if(request.getParameter("pageSize") != null){
            size = Integer.parseInt(request.getParameter("pageSize"));
        }
        info.put("status", CustomConstants.APP_STATUS_SUCCESS);
        info.put("statusDesc", CustomConstants.APP_STATUS_DESC_SUCCESS);

        AppProjectInvestBeanRequest form = new AppProjectInvestBeanRequest();
        form.setBorrowNid(borrowNid);
        form.setCurrPage(currentPage);
        form.setPageSize(size);
        appProjectListService.createProjectInvestPage(info, form);
        return info;
    }


    /**
     * app端债转列表数据
     * 原接口：com.hyjf.app.project.projectController.searchProjectList()
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端债转列表", notes = "APP端债转列表")
    @PostMapping(value = "/projectlist/creditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(@ModelAttribute @Valid ProjectListRequest request) {
        JSONObject result = null;
        try {
            result = appProjectListService.searchAppCreditList(request);
        } catch (Exception e) {
            result.put(CustomConstants.APP_STATUS,CustomConstants.APP_STATUS_FAIL);
            result.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_FAIL);
        }
        return result;
    }


    /**
     * app端债转详情
     * 原接口: com.hyjf.app.user.transfer.AppTransferController.searchTenderCreditDetail()
     * @param
     * @return
     */
    @ApiOperation(value = "APP端债转详情", notes = "APP端债转详情")
    @GetMapping(value = "/transfer/{transferId}", produces = "application/json; charset=utf-8")
    public Object getCreditDetail(@PathVariable String transferId , @RequestHeader(value = "token", required = false) String token) {
        JSONObject result= appProjectListService.getAppCreditDetail(transferId, token);
        return result;
    }


    /**
     * app端债转承接记录
     *  com.hyjf.app.user.transfer.AppTransferController.investRecord()
     */
    @ApiOperation(value="APP端债转承接记录",notes="APP端债转承接记录")
    @GetMapping(value = "/transfer/{transferId}/investRecord", produces = "application/json; charset=utf-8")
    public BaseResultBeanFrontEnd investRecord(@PathVariable("transferId") String transferId, Integer currentPage, Integer pageSize) {
        return appProjectListService.investRecord(transferId,currentPage,pageSize);
    }





    /**
     * app端计划列表数据
     * 原接口：com.hyjf.app.project.projectController.searchProjectList()
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端计划列表", notes = "APP端计划列表")
    @PostMapping(value = "/projectlist/planList", produces = "application/json; charset=utf-8")
    public Object getPlanList(@ModelAttribute @Valid ProjectListRequest request) {
        JSONObject result = null;
        try {
            result = appProjectListService.searchAppPlanList(request);
        } catch (Exception e) {
            result.put(CustomConstants.APP_STATUS,CustomConstants.APP_STATUS_FAIL);
            result.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_FAIL);
        }
        return result;

    }


    /**
     * app端计划详情
     * 原接口：com.hyjf.app.hjhplan.HjhPlanController.searchHjhPlanDetail()
     * @param
     * @return
     */
    @ApiOperation(value = "APP端计划详情", notes = "APP端计划详情")
    @GetMapping(value ="/plan/{planId}", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@PathVariable @Valid String planId , @RequestHeader(value = "token", required = false) String token) {
         JSONObject result = appProjectListService.getAppPlanDetail(planId, token);
        return result;
    }

    /**
     * app端计划标的组成
     * com.hyjf.app.hjhplan.HjhPlanController.searchHjhPlanBorrow()
     */
    @ApiOperation(value = "APP端计划标的组成", notes = "APP端计划标的组成")
    @GetMapping(value = "/plan/{planId}/borrowComposition",produces = "application/json; charset=utf-8")
    public HjhPlanBorrowResultBean searchHjhPlanBorrow(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @PathVariable String planId) {
        HjhPlanBorrowResultBean result = new HjhPlanBorrowResultBean();
        appProjectListService.searchHjhPlanBorrow(result, planId, currentPage, pageSize);
        return result;
    }

    /**
     * app 端汇计划的加入记录
     * com.hyjf.wechat.controller.hjh.WxHjhPlanController.searchHjhPlanAccede()
     */
    @ApiOperation(value = "APP端汇计划加入记录", notes = "APP端汇计划加入记录")
    @GetMapping(value = "/plan/{planId}/investRecord",produces = "application/json; charset=utf-8")
    public HjhPlanAccedeResultBean searchHjhPlanAccede(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @PathVariable String planId) {
        HjhPlanAccedeResultBean result = new HjhPlanAccedeResultBean();
        appProjectListService.getHjhPlanAccede(result, planId, currentPage, pageSize);
        return result;
    }

}
