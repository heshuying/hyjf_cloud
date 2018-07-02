package com.hyjf.cs.trade.controller.wechat.project;

import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.WechatProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 微信端标的列表和详情controller
 * @author zhangyk
 * @date 2018/7/2 11:15
 */
@Api(value = "Web端项目列表")
@RestController
@RequestMapping("/wechat/projectlist")
public class WechatProjectListController extends BaseTradeController {

    @Autowired
    private WechatProjectListService wechatProjectListService;


    /**
     * 微信端获取首页散标详情
     * @author zhangyk
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "微信端:获取首页散标详情", notes = "微信端:获取首页散标详情")
    @PostMapping(value = "/getProjectDetail", produces = "application/json; charset=utf-8")
    public Object getProjectDetail(@RequestBody Map<String,Object> map,  @RequestHeader(value = "token", required = false) String token){
        WeChatResult weChatResult = wechatProjectListService.getProjectDetail(map,token);
        return weChatResult;
    }


    /**
     * 微信端获取计划详情
     * @author zhangyk
     * @date 2018/7/2 16:28
     */
    @ApiOperation(value = "微信端:获取首页计划详情", notes = "微信端:获取首页计划详情")
    @PostMapping(value = "/getPlanDetail", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@RequestBody Map<String,Object> map,  @RequestHeader(value = "token", required = false) String token){
        WeChatResult weChatResult = wechatProjectListService.getPlanDetail(map,token);
        return weChatResult;
    }

    /**
     * 微信端获取债转详情
     * @author zhangyk
     * @date 2018/7/2 16:28
     */
    @ApiOperation(value = "微信端:获取首页债转详情", notes = "微信端:获取首页债转详情")
    @PostMapping(value = "/getCreditDetail", produces = "application/json; charset=utf-8")
    public Object getCreditDetail(@RequestBody Map<String,Object> map,  @RequestHeader(value = "token", required = false) String token){
        WeChatResult weChatResult = wechatProjectListService.getProjectDetail(map,token);
        return weChatResult;
    }
}
