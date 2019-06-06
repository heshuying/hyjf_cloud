package com.hyjf.cs.trade.controller.wechat.wjt;

import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.WechatHomePageResult;
import com.hyjf.cs.trade.service.home.WechatProjectListService;
import com.hyjf.cs.trade.service.wjt.WjtWechatProjectListService;
import com.hyjf.cs.trade.util.HomePageDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信端温金投首页数据controller
 *
 * @author pcc
 * @date 2018/7/23 15:45
 */
@Api(value = "weChat端-温金投首页", tags = "weChat端-温金投首页")
@RestController
@RequestMapping("/hyjf-wechat/wjt/homepage")
public class WjtWechatHomeController {


    @Autowired
    private WjtWechatProjectListService wjtWechatProjectListService;

    /**
     * 微信温金投首页项目列表
     * @author pcc
     * @date 2018/10/9 17:51
     */
    @ApiOperation(value = "首页项目列表" , notes = "首页项目了列表")
    @GetMapping(value = "/getHomeProjectList.do",produces = "application/json; charset=utf-8")
    public BaseResultBean getHomeProjectList(HttpServletRequest request,
                                             @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                             @RequestParam(value = "showPlanFlag") String showPlanFlag,
                                             @RequestHeader(value = "userId", required = false) Integer userId){
        WechatHomePageResult result = wjtWechatProjectListService.getHomeProejctList(currentPage,pageSize,showPlanFlag,userId);
        return result;
    }


}
