package com.hyjf.cs.trade.controller.app.user.myproject;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的散标详情
 * @author zhangyk
 * @date 2018/8/1 10:05
 */
@Api(value = "app端-用户我的散标详情",tags = "app端-用户我的散标详情")
@RestController
@RequestMapping("/hyjf-app/user/borrow")
public class AppMyProjectDetailController extends BaseTradeController {

    @Autowired
    private AppMyProjectService appMyProjectService;

    /**
     * 用户中心我的散标详情
     * @date 2018/7/2 16:27
     * 原接口：com.hyjf.app.user.credit.AppTenderCreditBorrowController.searchTenderCreditDetail()
     */
    @ApiOperation(value = "App端:获取我的散标详情", notes = "App端:获取我的散标详情")
    @GetMapping(value = "/{borrowId}", produces = "application/json; charset=utf-8")
    public Object MyProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader("userId") String userId){
       JSONObject result = appMyProjectService.getMyProjectDetail(borrowId,request,userId);
       return result;
    }
    
    /**
     * @author libin
     * App端:发送短信验证码(ajax请求)短信验证码数据保存(取自web)
     */
    @ApiOperation(value = "App端:发送短信验证码(ajax请求)短信验证码数据保存", notes = "App端:发送短信验证码(ajax请求)短信验证码数据保存")
    @PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
    public AppResult sendCode(@RequestBody TenderBorrowCreditCustomize request,@RequestHeader(value = "userId",required = false) Integer userId){
    	AppResult result = appMyProjectService.sendCreditCode(request,userId);
    	return result;
    }
    
    /**
     * @author libin
     * App端:用户中心债转提交保存(取自web)
     */
    @ApiOperation(value = "App端:用户中心债转提交保存", notes = "App端:用户中心债转提交保存")
    @PostMapping(value = "/saveTenderToCredit", produces = "application/json; charset=utf-8")
    public AppResult saveTenderToCredit(@RequestBody TenderBorrowCreditCustomize request,@RequestHeader(value = "userId",required = false) Integer userId){
    	request.setPlatform(Integer.parseInt(CommonConstant.CLIENT_PC));
    	AppResult result =  appMyProjectService.saveTenderToCredit(request,userId);
    	return result;
    }	
}
