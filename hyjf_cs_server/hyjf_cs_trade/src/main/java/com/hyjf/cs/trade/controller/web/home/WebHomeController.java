package com.hyjf.cs.trade.controller.web.home;

import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.service.home.WebHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web端首页controller
 * @author zhangyk
 * @date 2018/7/4 13:47
 */
@Api(value = "web端-首页",tags = "web端-首页")
@RestController
@RequestMapping("/hyjf-web/home")
public class WebHomeController extends BaseController {


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

    @ApiOperation(value = "安卓下载接口" , notes = "安卓下载接口")
    @GetMapping(value = "/androidDownload")
    public void androidDown(HttpServletResponse response){
        webHomeService.androidDownload(response);
    }

}
