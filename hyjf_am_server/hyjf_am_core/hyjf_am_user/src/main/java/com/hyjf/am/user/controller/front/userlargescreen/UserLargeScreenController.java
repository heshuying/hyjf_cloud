/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.response.trade.ScreenTransferResponse;
import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;
import com.hyjf.am.user.service.front.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = " 所有坐席和坐席下用户查询",notes = " 所有坐席和坐席下用户查询")
    @GetMapping(value = "/get_currentowners_and_userids")
    public UserLargeScreenTwoResponse getCurrentOwnersAndUserIds() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        List<MonthDataStatisticsVO> list = userLargeScreenService.getCurrentOwnersAndUserIds();
        UserLargeScreenTwoVO userLargeScreenTwoVO = new UserLargeScreenTwoVO();
        userLargeScreenTwoVO.setMonthDataStatisticsNew(list);
        response.setResult(userLargeScreenTwoVO);
        return response;
    }

    @ApiOperation(value = " 获取投屏采集的所有的用户信息",notes = " 获取投屏采集的所有的用户信息")
    @PostMapping(value = "/getscreentransferdata")
    public ScreenTransferResponse getScreenTransferData(@RequestBody List<ScreenTransferVO> userList) {
        ScreenTransferResponse response = userLargeScreenService.getScreenTransferData(userList);
        return response;
    }
}
