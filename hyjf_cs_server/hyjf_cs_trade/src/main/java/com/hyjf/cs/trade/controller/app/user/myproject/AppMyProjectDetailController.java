package com.hyjf.cs.trade.controller.app.user.myproject;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.myproject.AppMyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的散标详情
 * @author zhangyk
 * @date 2018/8/1 10:05
 */
@Api(value = "app端用户我的散标详情",description = "app端用户我的散标详情")
@Controller
@RequestMapping("/hyjf-app/user/borrow")
public class AppMyProjectDetailController extends BaseTradeController {

    @Autowired
    private AppMyProjectService appMyProjectService;

    /**
     * 用户中心我的散标详情
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "App端:获取我的散标信息", notes = "App端:获取我的散标信息")
    @PostMapping(value = "/{borrowId}", produces = "application/json; charset=utf-8")
    public Object MyProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader("userId") String userId){
       JSONObject result = appMyProjectService.getMyProjectDetail(borrowId,request,userId);
       return result;
    }
}
