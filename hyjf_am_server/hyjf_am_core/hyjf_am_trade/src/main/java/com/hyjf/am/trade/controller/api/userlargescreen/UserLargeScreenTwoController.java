/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.userlargescreen;

import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.response.trade.ScreenTransferResponse;
import com.hyjf.am.trade.service.api.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.api.UserLargeScreenTwoVO;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "投屏数据采集所有的运营部用户", notes = "投屏数据采集所有的运营部用户")
    @GetMapping(value = "/getallscreenuser/{start}/{sizes}")
    public ScreenTransferResponse getAllScreenUser(@PathVariable int start, @PathVariable int sizes) {
        ScreenTransferResponse response = new ScreenTransferResponse();
        try {
            List<ScreenTransferVO> list = userLargeScreenService.getAllScreenUser(start,sizes);
            response.setResultList(list);
        }catch (Exception e){
            logger.error("投屏数据采集所有的运营部用户异常,报文如下:", e);
        }
        return response;
    }

    @ApiOperation(value = "投屏划转对ht_user_operate_list表执行更新操作", notes = "投屏划转对ht_user_operate_list表执行更新操作")
    @PostMapping(value = "/updateoperatielist")
    public ScreenTransferResponse updateOperatieList(@RequestBody List<ScreenTransferVO> updateList) {
        ScreenTransferResponse response = new ScreenTransferResponse();
        try {
            userLargeScreenService.updateOperatieList(updateList);
            response.setRtn("true");
        }catch (Exception e){
            logger.error("投屏数据采集所有的运营部用户异常,报文如下:", e);
            response.setRtn("false");
        }
        return response;
    }

    @ApiOperation(value = "投屏划转对ht_user_operate_list表执行删除操作", notes = "投屏划转对ht_user_operate_list表执行删除操作")
    @PostMapping(value = "/deleteoperatielist")
    public ScreenTransferResponse deleteOperatieList(@RequestBody List<ScreenTransferVO> deleteList) {
        ScreenTransferResponse response = new ScreenTransferResponse();
        try {
            userLargeScreenService.deleteOperatieList(deleteList);
            response.setRtn("true");
        }catch (Exception e){
            logger.error("投屏数据采集所有的运营部用户异常,报文如下:", e);
            response.setRtn("false");
        }
        return response;
    }

    @ApiOperation(value = "投屏划转对ht_repayment_plan表执行更新操作", notes = "投屏划转对ht_repayment_plan表执行更新操作")
    @PostMapping(value = "/updaterepaymentplan")
    public ScreenTransferResponse updateRepaymentPlan(@RequestBody List<ScreenTransferVO> updateList) {
        ScreenTransferResponse response = new ScreenTransferResponse();
        try {
            userLargeScreenService.updateRepaymentPlan(updateList);
            response.setRtn("true");
        }catch (Exception e){
            logger.error("投屏数据采集所有的运营部用户异常,报文如下:", e);
            response.setRtn("false");
        }
        return response;
    }

    @ApiOperation(value = "投屏划转对ht_repayment_plan表执行删除操作", notes = "投屏划转对ht_repayment_plan表执行删除操作")
    @PostMapping(value = "/deleterepaymentplan")
    public ScreenTransferResponse deleteRepaymentPlan(@RequestBody List<ScreenTransferVO> deleteList) {
        ScreenTransferResponse response = new ScreenTransferResponse();
        try {
            userLargeScreenService.deleteRepaymentPlan(deleteList);
            response.setRtn("true");
        }catch (Exception e){
            logger.error("投屏数据采集所有的运营部用户异常,报文如下:", e);
            response.setRtn("false");
        }
        return response;
    }
}
