package com.hyjf.cs.trade.controller.wechat.project;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.home.WechatProjectListService;
import com.hyjf.cs.trade.util.ProjectConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信端标的列表和详情controller
 * @author zhangyk
 * @date 2018/7/2 11:15
 */
@Api(tags = "WeChat端项目详情")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/wechat")
public class WechatProjectListController extends BaseTradeController {

    @Autowired
    private WechatProjectListService wechatProjectListService;


    /**
     * 微信端获取首页散标详情
     * @author zhangyk
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "微信端:获取首页<散标>详情", notes = "微信端:获取首页<散标>详情")
    @PostMapping(value = "/borrow/{borrowId}.do", produces = "application/json; charset=utf-8")
    public Object getProjectDetail(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "userId", required = false) Integer userId){
        JSONObject jsonObject = wechatProjectListService.getProjectDetail(borrowId,request.getParameter(ProjectConstant.PARAM_BORROW_TYPE),userId);
        return jsonObject;
    }

    /**
     * 微信端获取首页散标加入记录
     * @author zhangyk
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "微信端:获取首页<散标>加入记录", notes = "微信端:获取首页<散标>加入记录")
    @PostMapping(value = "/borrow/{borrowId}/investRecord.do", produces = "application/json; charset=utf-8")
    public Object getProjectInvestRecord(@PathVariable String borrowId, HttpServletRequest request, @RequestHeader(value = "userId", required = false) String userId){
        JSONObject jsonObject = wechatProjectListService.getProjectInvestRecord(borrowId,request,userId);
        return jsonObject;
    }


    /**
     * 微信端获取计划详情
     * @author zhangyk
     * @date 2018/7/2 16:28
     */
    @ApiOperation(value = "微信端:获取首页<计划>详情", notes = "微信端:获取首页<计划>详情")
    @PostMapping(value = "/plan/{planId}", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@PathVariable String planId,  @RequestHeader(value = "userId", required = false) Integer userId){
        JSONObject jsonObject = wechatProjectListService.getPlanDetail(planId,userId);
        return jsonObject;
    }

      /**
     * 微信端获取计划标的组成
     * @author zhangyk
     * @date 2018/7/30 11:00
     */
    @ApiOperation(value = "微信端:获取首页<计划>标的组成", notes = "微信端:获取首页<计划>标的组成")
    @PostMapping(value = "/plan/{planId}/borrowComposition.do", produces = "application/json; charset=utf-8")
    public Object getPlanBorrowList( @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @PathVariable String planId){
        Object jsonObject = wechatProjectListService.getPlanBorrowList(planId,currentPage,pageSize);
        return jsonObject;
    }



    /**
     * 微信端获取计划加入记录
     * @author zhangyk
     * @date 2018/7/30 11:00
     */
    @ApiOperation(value = "微信端:获取首页<计划>加入记录", notes = "微信端:获取首页<计划>加入记录")
    @PostMapping(value = "/plan/{planId}/investRecord.do", produces = "application/json; charset=utf-8")
    public Object getPlanAccedeList( @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @PathVariable String planId){
        Object jsonObject = wechatProjectListService.getPlanAccedeList(planId,currentPage,pageSize);
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
