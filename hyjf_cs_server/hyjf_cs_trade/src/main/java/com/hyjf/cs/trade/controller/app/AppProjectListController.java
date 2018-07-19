/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app;

import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.AppProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 移动端项目列表
 *
 * @author zhangyk
 *
 */
@Api(description = "移动端项目列表")
@RestController
@RequestMapping("/hyjf-app/projectlist")
public class AppProjectListController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(AppProjectListController.class);

     @Autowired
     private AppProjectListService appProjectListService;

    /**
     * app端获取散标投资列表
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端散标列表", notes = "APP投资散标列表")
    @PostMapping(value = "/borrowProjectList", produces = "application/json; charset=utf-8")
    public Object homeBorrowProjectList(@RequestBody @Valid AppProjectListRequest request){
        // controller 不做业务处理
        AppResult result =  appProjectListService.searchAppProjectList(request);
        return result;
    }

    /**
     * app端获取散标投资列表
     * @param param
     * @return
     */
    @ApiOperation(value = "APP端散标详情", notes = "APP端散标详情")
    @ApiImplicitParam(name = "param",value = "{borrowId:string,borrowType:string<1是债转,0 普通>}", dataType = "Map")
    @PostMapping(value = "/borrowProjectDetail", produces = "application/json; charset=utf-8")
    public Object borrowProejctDetail(@RequestBody Map<String,String> param,  @RequestHeader(value = "token", required = false) String token){
        // controller 不做业务处理
        AppResult result =  appProjectListService.getAppProjectDetail(param,token);
        return result;
    }


    /**
     * app端债转列表数据
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端债转列表", notes = "APP端债转列表")
    @PostMapping(value = "/creditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(@RequestBody @Valid ProjectListRequest request){
        AppResult result =  appProjectListService.searchAppCreditList(request);
        return result;
    }


    /**
     * app端债转详情
     * @param param
     * @return
     */
    @ApiOperation(value = "APP端债转详情", notes = "APP端债转详情")
    @PostMapping(value = "/creditDetail", produces = "application/json; charset=utf-8")
    public Object getCreditDetail(@RequestBody @Valid Map<String,String> param,@RequestHeader(value = "token", required = false) String token){
        AppResult result =  appProjectListService.getAppCreditDetail(param,token);
        return result;
    }



    /**
     * app端计划列表数据
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端计划列表", notes = "APP端计划列表")
    @PostMapping(value = "/planList", produces = "application/json; charset=utf-8")
    public Object getPlanList(@RequestBody @Valid ProjectListRequest request){
        AppResult result =  appProjectListService.searchAppPlanList(request);
        return result;
    }



    /**
     * app端计划详情
     * @param
     * @return
     */
    @ApiOperation(value = "APP端计划详情", notes = "APP端计划详情")
    @PostMapping(value = "/planDetail", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@RequestBody @Valid Map<String,String> param,@RequestHeader(value = "token", required = false) String token){
        AppResult result =  appProjectListService.getAppPlanDetail(param,token);
        return result;
    }





}
