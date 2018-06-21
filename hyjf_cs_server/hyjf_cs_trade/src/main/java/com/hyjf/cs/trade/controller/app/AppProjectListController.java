/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.AppProjectListService;
import com.hyjf.cs.trade.service.WebProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 移动端项目列表
 *
 * @author zhangyk
 *
 */
@Api(value = "移动端项目列表")
@RestController
@RequestMapping("/app/projectlist")
public class AppProjectListController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(AppProjectListController.class);

     @Autowired
     private AppProjectListService appProjectListService;

    /**
     * app端获取散标投资列表
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端投资散标列表", notes = "APP投资散标列表")
    @PostMapping(value = "/homeBorrowProjectList", produces = "application/json; charset=utf-8")
    public Object homeBorrowProjectList(@RequestBody @Valid ProjectListRequest request){
        // controller 不做业务处理
        AppResult result =  appProjectListService.searchAppProjectList(request);
        return result;
    }


    /**
     * app端债转列表数据
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端债转列表", notes = "APP端债转列表")
    @PostMapping(value = "/getCreditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(@RequestBody @Valid ProjectListRequest request){
        AppResult result =  appProjectListService.searchAppCreditList(request);
        return result;
    }





}
