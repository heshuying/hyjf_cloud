/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

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
    public UserLargeScreenVO getScalePerformance() {
        UserLargeScreenVO vo = userLargeScreenService.getScalePerformance();
        return vo;
    }
    @ApiOperation(value = " 坐席月规模业绩(新老客户组所有数据)",notes = " 坐席月规模业绩(新老客户组所有数据)")
    @GetMapping(value = "/getmonthscaleperformancelist")
    public UserLargeScreenVO getMonthScalePerformanceList() {
        UserLargeScreenVO vo = userLargeScreenService.getMonthScalePerformanceList();
        return vo;
    }

    @ApiOperation(value = " 运营部总业绩",notes = " 运营部总业绩")
    @GetMapping(value = "/gettotalamount")
    public UserLargeScreenVO getTotalAmount() {
        UserLargeScreenVO vo = userLargeScreenService.getTotalAmount();
        return vo;
    }

    @ApiOperation(value = " 运营部业绩完成分布",notes = " 运营部业绩完成分布")
    @GetMapping(value = "/getachievementdistributionlist")
    public UserLargeScreenVO getAchievementDistributionList() {
        UserLargeScreenVO vo = userLargeScreenService.getAchievementDistributionList();
        return vo;
    }
    @ApiOperation(value = " 坐席月回款情况(新老客户组所有数据)",notes = " 坐席月回款情况(新老客户组所有数据)")
    @GetMapping(value = "/getmonthreceivedpayments")
    public UserLargeScreenVO getMonthReceivedPayments() {
        UserLargeScreenVO vo = userLargeScreenService.getMonthReceivedPayments();
        return vo;
    }

}
