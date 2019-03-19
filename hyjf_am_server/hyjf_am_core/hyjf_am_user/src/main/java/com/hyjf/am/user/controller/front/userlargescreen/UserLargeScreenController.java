/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.userlargescreen;

import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;
import com.hyjf.am.user.service.front.userlargescreen.UserLargeScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanyy
 * @version UserLargeScreenController, v0.1 2019/3/18 10:22
 */
@Api(value = "用户大屏接口", tags = "用户大屏接口")
@RestController
@RequestMapping("/am-user/user_large_screen")
public class UserLargeScreenController {

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = " 大屏幕运营部配置获取",notes = " 大屏幕运营部配置获取")
    @PostMapping(value = "/screenconfig")
    public UserScreenConfigResponse getScreenConfig(@RequestBody UserLargeScreenRequest request) {
        UserScreenConfigResponse response = userLargeScreenService.getScreenConfig(request);
        return response;
    }

    @ApiOperation(value = " 坐席月任务配置",notes = " 坐席月任务配置")
    @PostMapping(value = "/customertaskconfig")
    public UserCustomerTaskConfigResponse getCustomerTaskConfig(@RequestBody UserLargeScreenRequest request) {
        UserCustomerTaskConfigResponse response = userLargeScreenService.getCustomerTaskConfig(request);
        return response;
    }
}
