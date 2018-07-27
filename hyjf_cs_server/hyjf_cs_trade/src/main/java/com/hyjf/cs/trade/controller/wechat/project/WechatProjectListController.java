package com.hyjf.cs.trade.controller.wechat.project;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.WechatProjectListService;
import com.hyjf.cs.trade.util.ProjectConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信端标的列表和详情controller
 * @author zhangyk
 * @date 2018/7/2 11:15
 */
@Api(value = "Web端项目详情")
@RestController
@RequestMapping(value = {"/hyjf-wechat/wx/bank/wechat/plan","hyjf-wechat/wx/bank/wechat/borrow"})
public class WechatProjectListController extends BaseTradeController {

    @Autowired
    private WechatProjectListService wechatProjectListService;


    /**
     * 微信端获取首页散标详情
     * @author zhangyk
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "微信端:获取首页散标详情", notes = "微信端:获取首页散标详情")
    @PostMapping(value = "/{borrowId}", produces = "application/json; charset=utf-8")
    public Object getProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "token", required = false) String token){
        JSONObject jsonObject = wechatProjectListService.getProjectDetail(borrowId,request.getParameter(ProjectConstant.PARAM_BORROW_TYPE),token);
        return jsonObject;
    }


    /**
     * 微信端获取计划详情
     * @author zhangyk
     * @date 2018/7/2 16:28
     */
    @ApiOperation(value = "微信端:获取首页计划详情", notes = "微信端:获取首页计划详情")
    @PostMapping(value = "/{planId}", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@PathVariable String planId,  @RequestHeader(value = "token", required = false) String token){
        JSONObject jsonObject = wechatProjectListService.getPlanDetail(planId,token);
        return jsonObject;
    }

   /* *//**
     * 微信端获取债转详情
     * @author zhangyk
     * @date 2018/7/2 16:28
     *//*
    @ApiOperation(value = "微信端:获取首页债转详情", notes = "微信端:获取首页债转详情")
    @PostMapping(value = "/getCreditDetail", produces = "application/json; charset=utf-8")
    public Object getCreditDetail(@RequestBody Map<String,Object> map,  @RequestHeader(value = "token", required = false) String token){
        WeChatResult weChatResult = wechatProjectListService.getProjectDetail(map,token);
        return weChatResult;
    }*/
}
