/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "运营大屏接口", tags = "运营大屏接口")
@RestController
@RequestMapping("/am-trade/user_large_screen_two")
public class UserLargeScreenTwoController {

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = "屏幕二日业绩(新客组、老客组)", notes = "屏幕二日业绩(新客组、老客组)")
    @GetMapping(value = "/getdayscaleperformancelist")
    public UserLargeScreenTwoResponse getDayScalePerformanceList() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getDayScalePerformanceList();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "屏幕二日回款(新客组、老客组)", notes = "屏幕二日回款(新客组、老客组)")
    @GetMapping(value = "/getdayreceivedpayments")
    public UserLargeScreenTwoResponse getDayReceivedPayments() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getDayReceivedPayments();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "屏幕二本月数据统计(新客组、老客组)", notes = "屏幕二本月数据统计(新客组、老客组)")
    @PostMapping(value = "/getmonthdatastatistics")
    public UserLargeScreenTwoResponse getMonthDataStatistics(@RequestBody List<MonthDataStatisticsVO> currentOwnersAndUserIds) {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getMonthDataStatistics(currentOwnersAndUserIds);
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = "屏幕二运营部月度业绩数据", notes = "屏幕二运营部月度业绩数据")
    @GetMapping(value = "/getopermonthperformancedata")
    public UserLargeScreenTwoResponse getOperMonthPerformanceData() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        UserLargeScreenTwoVO vo = userLargeScreenService.getOperMonthPerformanceData();
        response.setResult(vo);
        return response;
    }
}
