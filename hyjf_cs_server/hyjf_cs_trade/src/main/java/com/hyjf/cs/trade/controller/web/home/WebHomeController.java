package com.hyjf.cs.trade.controller.web.home;

import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.service.home.WebHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Web端首页controller
 * @author zhangyk
 * @date 2018/7/4 13:47
 */
@Api(value = "Web端首页",tags = "Web端首页")
@RestController
@RequestMapping("/hyjf-web/home")
public class WebHomeController {


    @Autowired
    private WebHomeService webHomeService;


    /**
     * 获取首页数据接口
     * @return
     */
    @ApiOperation(value = "获取首页各项数据", notes = "获取首页各项数据")
    @PostMapping(value = "/homeData", produces = "application/json; charset=utf-8")
    public Object getHomeData(@RequestHeader(value = "userId" , required = false )String userId){
        // controller 不做业务处理
        WebResult result =  webHomeService.getHomeData(userId);
        return result;
    }

}
