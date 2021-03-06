/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.pointsshop;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.bean.DuiBaPointsDetailRequestBean;
import com.hyjf.cs.user.service.pointsshop.DuiBaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjun
 * @version DuiBaController, v0.1 2019/5/29 14:08
 */
@Api(value = "weChat端-兑吧相关接口", tags = "weChat端-兑吧相关接口")
@RestController
@RequestMapping("/hyjf-wechat")
public class WeChatDuiBaController {
    @Autowired
    DuiBaService duiBaService;

    @ApiOperation(value = "获取兑吧跳转url", notes = "获取兑吧跳转url")
    @PostMapping(value = "/pointsshop/duiba/geturl")
    public JSONObject getUrl(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request){
        return duiBaService.getUrl(userId, request);
    }

    @ApiOperation(value = "微信端获取配置接口", notes = "微信端获取配置接口")
    @PostMapping(value = "/getconfig")
    public JSONObject getConfig(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request){
        return duiBaService.getConfig(userId, request);
    }

    @ApiOperation(value = "获取兑吧积分明细", notes = "获取兑吧积分明细")
    @PostMapping(value = "/pointsshop/duiba/getpointsdetail")
    public JSONObject getPointsDetail(@RequestHeader(value = "userId") Integer userId, @RequestBody DuiBaPointsDetailRequestBean requestBean){
        return duiBaService.getPointsDetail(userId, requestBean);
    }

    @ApiOperation(value = "获取用户当前积分", notes = "获取用户当前积分")
    @PostMapping(value = "/pointsshop/duiba/getuserpoints")
    public JSONObject getUserPoints(@RequestHeader(value = "userId") Integer userId){
        return duiBaService.getUserPoints(userId);
    }

}
