package com.hyjf.cs.trade.controller.app.home;

import com.hyjf.cs.trade.service.AppHomeService;
import com.hyjf.cs.trade.util.AppHomePageDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * APP端首页controller
 * @author zhangyk
 * @date 2018/7/5 13:40
 */
@Api(value = "Web端首页")
@RestController
//@RequestMapping("/hyjf-app/home")
@RequestMapping(AppHomePageDefine.REQUEST_MAPPING)  // 保留原来请去路径
public class AppHomeController {


    @Autowired
    private AppHomeService appHomeService;


    /**
     * 获取首页数据接口
     * 沿用原来的接口路径
     * @return
     */
    @ApiOperation(value = "APP:获取首页各项数据", notes = "APP:获取首页各项数据")
    @PostMapping(value = AppHomePageDefine.PROJECT_LIST_ACTION, produces = "application/json; charset=utf-8")
    public Object getHomeData(HttpServletRequest request , @RequestHeader(value = "userId" , required = false )String userId){
        // controller 不做业务处理
        Object object =  appHomeService.getAppHomeData(request,userId);
        return object;
    }

}
