package com.hyjf.cs.trade.controller.app.user.mycredit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的债转详情
 * @author zhangyk
 * @date 2018/8/30 13:49
 */
@Api(value = "app端-用户我的债转详情",tags = "app端-用户我的债转详情")
@RestController
@RequestMapping("/hyjf-app/user/transfer")
public class AppMyCreditDetailController extends BaseTradeController {


    @Autowired
    private AppMyProjectService appMyProjectService;

    @ApiOperation(value = "App端:获取我的债转详情", notes = "App端:获取我的债转详情")
    @GetMapping(value = "/{transfId}", produces = "application/json; charset=utf-8")
    public Object MyProjectDetail(@PathVariable String transfId, HttpServletRequest request, @RequestHeader(value = "userId",required = false) Integer userId){
        JSONObject result = appMyProjectService.getMyCreditDetail(transfId,request,userId);
        return result;
    }
}
