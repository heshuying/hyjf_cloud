/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "运营大屏接口", tags = "运营大屏接口")
@RestController
@RequestMapping("/am-trade/user_large_screen_two")
public class UserLargeScreenTwoController {
    private static final Logger logger = LoggerFactory.getLogger(UserLargeScreenTwoController.class);

    @Autowired
    private UserLargeScreenService userLargeScreenService;

    @ApiOperation(value = "屏幕二日业绩(新客组、老客组)", notes = "屏幕二日业绩(新客组、老客组)")
    @GetMapping(value = "/getdayscaleperformancelist")
    public UserLargeScreenTwoResponse getDayScalePerformanceList() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        try {
            UserLargeScreenTwoVO vo = userLargeScreenService.getDayScalePerformanceList();
            response.setResult(vo);
        }catch (Exception e){
            logger.error("用户画像-运营大屏 屏幕二日业绩(新客组、老客组) 数据获取异常,报文如下:", e);
        }
        return response;
    }

    @ApiOperation(value = "屏幕二日回款(新客组、老客组)", notes = "屏幕二日回款(新客组、老客组)")
    @GetMapping(value = "/getdayreceivedpayments")
    public UserLargeScreenTwoResponse getDayReceivedPayments() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        try {
            UserLargeScreenTwoVO vo = userLargeScreenService.getDayReceivedPayments();
            response.setResult(vo);
        }catch (Exception e){
            logger.error("用户画像-运营大屏 屏幕二日回款(新客组、老客组) 数据获取异常,报文如下:", e);
        }
        return response;
    }

    @ApiOperation(value = "屏幕二本月数据统计(新客组、老客组)", notes = "屏幕二本月数据统计(新客组、老客组)")
    @GetMapping(value = "/getmonthdatastatistics")
    public UserLargeScreenTwoResponse getMonthDataStatistics() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        try {
            UserLargeScreenTwoVO vo = userLargeScreenService.getMonthDataStatistics();
            response.setResult(vo);
        }catch (Exception e){
            logger.error("用户画像-运营大屏 屏幕二本月数据统计(新客组、老客组) 数据获取异常,报文如下:", e);
        }
        return response;
    }

    @ApiOperation(value = "屏幕二运营部月度业绩数据", notes = "屏幕二运营部月度业绩数据")
    @GetMapping(value = "/getopermonthperformancedata")
    public UserLargeScreenTwoResponse getOperMonthPerformanceData() {
        UserLargeScreenTwoResponse response = new UserLargeScreenTwoResponse();
        try {
            UserLargeScreenTwoVO vo = userLargeScreenService.getOperMonthPerformanceData();
            response.setResult(vo);
        }catch (Exception e){
            logger.error("用户画像-运营大屏 屏幕二运营部月度业绩数据 数据获取异常,报文如下:", e);
        }
        return response;
    }
}
