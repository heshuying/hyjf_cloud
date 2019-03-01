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
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "app端-项目列表")
@RestController
@RequestMapping(ProjectConstant.REQUEST_HOME )
public class AppProjectListController extends BaseTradeController {

    @Autowired
    private AppProjectListService appProjectListService;

    /**
     * app端获取散标出借列表
     * 原接口：com.hyjf.app.project.projectController.searchProjectList()
     * @param request
     * @return
     */
    @ApiOperation(value = "散标列表", notes = "出借散标列表")
    @PostMapping(value ="/projectlist/borrowProjectList", produces = "application/json; charset=utf-8")
    public Object homeBorrowProjectList(@ModelAttribute @Valid ProjectListRequest request) {
        // controller 不做业务处理
        JSONObject result = appProjectListService.searchAppProjectList(request);
        return result;
    }

    /**
     * app端获取散标出借详情
     * 原接口：com.hyjf.app.project.BorrowProjectController.searchProjectDetail()
     * @param
     * @return
     */
    @ApiOperation(value = "散标详情", notes = "端散标详情")
    @ApiImplicitParam(name = "param", value = "{borrowId:string,borrowType:string<1是债转,0 普通>}", dataType = "Map")
    @GetMapping(value = "/borrow/{borrowId}", produces = "application/json; charset=utf-8")
    public Object borrowProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "userId", required = false) Integer userId) {
        // controller 不做业务处理
        JSONObject result = appProjectListService.getAppProjectDetail(borrowId,request, userId);
        return result;
    }

    /**
     * app端获取散标出借记录
     * add by jijun 20180726
     * 原接口:com.hyjf.app.project.BorrowProjectController.searchProjectInvestList()
     */
    @ApiOperation(value = "散标出借记录", notes = "散标出借记录")
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
        info.put("status", BaseResultBeanFrontEnd.SUCCESS);
        info.put("statusDesc", BaseResultBeanFrontEnd.SUCCESS_MSG);

        AppProjectInvestBeanRequest form = new AppProjectInvestBeanRequest();
        form.setBorrowNid(borrowNid);
        form.setCurrPage(currentPage);
        form.setPageSize(size);
        appProjectListService.createProjectInvestPage(info, form);
        return info;
    }


    /**
     * 计划的标的组成列表中，有可能包含债转标的
     * @author zhangyk
     * @date 2018/8/9 10:36
     * 原接口 com.hyjf.app.project.BorrowProjectController.searchProjectUndertakeList()
     */
    @ApiOperation(value = "新增承接记录列表显示", notes = "新增承接记录列表显示" )
    @GetMapping(value = "/borrow/{borrowId}/getBorrowUndertake" , produces = "application/json; charset=utf-8")
    public JSONObject searchProjectUndertakeList(@PathVariable("borrowId") String borrowNid, HttpServletRequest request) {
       JSONObject result =  appProjectListService.searchProjectUndertakeList(borrowNid,request);
       return result;
    }

    /**
     * app端债转列表数据
     * 原接口：com.hyjf.app.project.projectController.searchProjectList()
     * @param request
     * @return
     */
    @ApiOperation(value = "债转列表", notes = "债转列表")
    @PostMapping(value = "/projectlist/creditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(@ModelAttribute @Valid ProjectListRequest request) {
        JSONObject result = new JSONObject();
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
    @ApiOperation(value = "债转详情", notes = "债转详情")
    @GetMapping(value = "/transfer/{transferId}", produces = "application/json; charset=utf-8")
    public Object getCreditDetail(@PathVariable String transferId , @RequestHeader(value = "userId", required = false) String userId) {
        JSONObject result;
        if (StringUtils.isNotBlank(userId)){
            result = appProjectListService.getAppCreditDetail(transferId, Integer.valueOf(userId));
        }else{
            result = appProjectListService.getAppCreditDetail(transferId, null);
        }
        return result;
    }


    /**
     * app端债转承接记录
     *  com.hyjf.app.user.transfer.AppTransferController.investRecord()
     */
    @ApiOperation(value="债转承接记录",notes="债转承接记录")
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
    @ApiOperation(value = "计划列表", notes = "计划列表")
    @PostMapping(value = "/projectlist/planList", produces = "application/json; charset=utf-8")
    public Object getPlanList(@ModelAttribute @Valid ProjectListRequest request) {
        JSONObject result = new JSONObject();
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
    @ApiOperation(value = "计划详情", notes = "计划详情")
    @GetMapping(value ="/plan/{planId}", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@PathVariable @Valid String planId , @RequestHeader(value = "userId", required = false) String userId) {
        JSONObject result;
        if (StringUtils.isNotBlank(userId)){
            result = appProjectListService.getAppPlanDetail(planId, Integer.valueOf(userId));
        }else{
            result = appProjectListService.getAppPlanDetail(planId, null);
        }
        return result;
    }

    /**
     * app端计划标的组成
     * com.hyjf.app.hjhplan.HjhPlanController.searchHjhPlanBorrow()
     */
    @ApiOperation(value = "计划标的组成", notes = "计划标的组成")
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
    @ApiOperation(value = "汇计划加入记录", notes = "汇计划加入记录")
    @GetMapping(value = "/plan/{planId}/investRecord",produces = "application/json; charset=utf-8")
    public HjhPlanAccedeResultBean searchHjhPlanAccede(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @PathVariable String planId) {
        HjhPlanAccedeResultBean result = new HjhPlanAccedeResultBean();
        appProjectListService.getHjhPlanAccede(result, planId, currentPage, pageSize);
        return result;
    }

}
