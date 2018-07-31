package com.hyjf.cs.trade.controller.wechat.home;

import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.WechatHomePageResult;
import com.hyjf.cs.trade.service.WechatProjectListService;
import com.hyjf.cs.trade.util.HomePageDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信端首页数据controller
 *
 * @author zhangyk
 * @date 2018/7/23 15:45
 */
@Api(value = "Wechat端首页", description = "Wechat端首页")
@RestController
@RequestMapping(HomePageDefine.WECHAT_REQUEST_MAPPING)
public class WechatHomeController {


    @Autowired
    private WechatProjectListService wechatProjectListService;

    /**
     * 微信端首页统计数据
     *
     * @author zhangyk
     * @date 2018/7/23 16:16
     */
    @ApiOperation(value = "微信端:获取首页统计数据", notes = "微信端:获取首页统计数据")
    @GetMapping(value = HomePageDefine.WECHAT_HOME_INDEX_DATA_METHOD, produces = "application/json; charset=utf-8")
    public WechatHomePageResult getHomeData(@RequestHeader(value = "userId", required = false) String userId) {
        WechatHomePageResult result = new WechatHomePageResult();
        result = wechatProjectListService.getHomeIndexData(userId);
        return result;
    }


    @ApiOperation(value = "微信端:首页项目列表" , notes = "微信端：首页项目了列表")
    @GetMapping(value = HomePageDefine.WECHAT_HOME_PROJECT_LIST_METHOD,produces = "application/json; charset=utf-8")
    public BaseResultBean getHomeProjectList(HttpServletRequest request,
                                             @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                             @RequestParam(value = "showPlanFlag") String showPlanFlag,
                                             @RequestHeader(value = "token", required = false) String token){
        WechatHomePageResult result = wechatProjectListService.getHomeProejctList(currentPage,pageSize,showPlanFlag,token);
        return result;
    }


}
