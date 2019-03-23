/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.userlargescreen;

import com.hyjf.cs.trade.bean.UserLargeScreenResultBean;
import com.hyjf.cs.trade.bean.UserLargeScreenTwoResultBean;
import com.hyjf.cs.trade.service.userlargescreen.UserLargeScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanyy
 * @version UserLargeScreenController, v0.1 2019/3/18 10:22
 */
@Api(value = "用户大屏接口", tags = "用户大屏接口")
@RestController
@RequestMapping("/hyjf-api/user_large_screen")
public class UserLargeScreenController {

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = " 屏幕一数据获取",notes = " 屏幕一数据获取")
    @GetMapping(value = "/one")
    public UserLargeScreenResultBean getOnePage() {
        UserLargeScreenResultBean bean = userLargeScreenService.getOnePage();
        return bean;
    }

    @ApiOperation(value = " 屏幕二数据获取", notes = " 屏幕二数据获取")
    @GetMapping(value = "/two")
    public UserLargeScreenTwoResultBean getTwoPage() {
        UserLargeScreenTwoResultBean bean = userLargeScreenService.getTwoPage();
        return bean;
    }
}
