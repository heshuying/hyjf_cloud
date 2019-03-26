/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户大屏二接口", tags = "用户大屏二接口")
@RestController
@RequestMapping("/am-trade/user_large_screen_two")
public class UserLargeScreenTwoController {

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = "坐席日规模业绩(新老客户组所有数据)",notes = " 坐席日规模业绩(新老客户组所有数据)")
    @GetMapping(value = "/getdayscaleperformancelist")
    public UserLargeScreenTwoResponse getDayScalePerformanceList() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getDayScalePerformanceList();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "坐席日回款情况(新老客户组所有数据)",notes = " 坐席日回款情况(新老客户组所有数据)")
    @GetMapping(value = "/getdayreceivedpayments")
    public UserLargeScreenTwoResponse getDayReceivedPayments() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getDayReceivedPayments();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "坐席日回款情况(新老客户组所有数据)",notes = " 坐席日回款情况(新老客户组所有数据)")
    @GetMapping(value = "/getmonthdatastatistics")
    public UserLargeScreenTwoResponse getMonthDataStatistics() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getMonthDataStatistics();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "运营部月度业绩数据",notes = " 运营部月度业绩数据")
    @GetMapping(value = "/getopermonthperformancedata")
    public UserLargeScreenTwoResponse getOperMonthPerformanceData() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getOperMonthPerformanceData();
        response.setResult(vo);
        return response;
    }
}
