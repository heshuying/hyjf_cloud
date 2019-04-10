/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenResponse;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.UserLargeScreenVO;
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
@RequestMapping("/am-trade/user_large_screen")
public class UserLargeScreenController {

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = " 规模业绩(新老客户组所有数据)",notes = " 规模业绩(新老客户组所有数据)")
    @GetMapping(value = "/getscaleperformance")
    public UserLargeScreenResponse getScalePerformance() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getScalePerformance();
        response.setResult(vo);
        return response;
    }
    @ApiOperation(value = " 坐席月规模业绩(新老客户组所有数据)",notes = " 坐席月规模业绩(新老客户组所有数据)")
    @GetMapping(value = "/getmonthscaleperformancelist")
    public UserLargeScreenResponse getMonthScalePerformanceList() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getMonthScalePerformanceList();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = " 运营部总业绩",notes = " 运营部总业绩")
    @GetMapping(value = "/gettotalamount")
    public UserLargeScreenResponse getTotalAmount() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getTotalAmount();
        response.setResult(vo);
        return response;
    }

    @ApiOperation(value = " 运营部业绩完成分布",notes = " 运营部业绩完成分布")
    @GetMapping(value = "/getachievementdistributionlist")
    public UserLargeScreenResponse getAchievementDistributionList() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getAchievementDistributionList();
        response.setResult(vo);
        return response;
    }
    @ApiOperation(value = " 坐席月回款情况(新老客户组所有数据)",notes = " 坐席月回款情况(新老客户组所有数据)")
    @GetMapping(value = "/getmonthreceivedpayments")
    public UserLargeScreenResponse getMonthReceivedPayments() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getMonthReceivedPayments();
        response.setResult(vo);
        return response;
    }
    @ApiOperation(value = " 用户资金明细",notes = " 用户资金明细")
    @GetMapping(value = "/getusercapitaldetails")
    public UserLargeScreenResponse getUserCapitalDetails() {
        UserLargeScreenResponse response = new UserLargeScreenResponse();
        UserLargeScreenVO vo = userLargeScreenService.getUserCapitalDetails();
        response.setResult(vo);
        return response;
    }
}
