/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.controller;

import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.response.Interest;
import com.hyjf.data.response.JcScreenResponse;
import com.hyjf.data.result.ApiResult;
import com.hyjf.data.service.JinChuangDataService;
import com.hyjf.data.vo.jinchuang.JcCustomerServiceVO;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;
import com.hyjf.data.vo.jinchuang.JcUserAnalysisVO;
import com.hyjf.data.vo.jinchuang.JcUserConversionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author yaoyong
 * @version JinChuangScreenController, v0.1 2019/6/19 9:23
 */
@Api(value = "金创展厅大屏", tags = "金创展厅大屏数据")
@RestController
@RequestMapping("/hyjf-web/jinChuangScreen")
public class JinChuangScreenController {

    @Autowired
    private JinChuangDataService jinChuangDataService;

    @ApiOperation(value = "获取大屏数据", notes = "获取大屏数据")
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public ApiResult getData() {
        JcScreenResponse response = new JcScreenResponse();
        JcUserConversionVO userConversion = jinChuangDataService.getUserConversion();
        String jsonObject = jinChuangDataService.getUserPoint();
        List<JcDataStatisticsVO> dataStatisticsList = jinChuangDataService.getDataStatistics();
        Map<String, Object> maps = jinChuangDataService.getUserAnalysis();
        JcTradeAmount tradeAmount = jinChuangDataService.getTradeAmount();
        if (tradeAmount != null) {
            response.setTradeAmount(tradeAmount);
        }
        List<JcRegisterTrade> registerTrades = jinChuangDataService.getRegisterTrade();
        if (!CollectionUtils.isEmpty(registerTrades)) {
            response.setRegisterTrades(registerTrades);
        }
        JcCustomerServiceVO customerService = jinChuangDataService.getCustomerService();
        response.setUserConversion(userConversion);
        response.setJsonObject(jsonObject);
        response.setDataStatisticsList(dataStatisticsList);
        response.setInterests((List<Interest>) maps.get("interests"));
        response.setAnalysis((JcUserAnalysisVO) maps.get("jcUserAnalysis"));
        response.setCustomerService(customerService);
        if (response != null) {
            return new ApiResult(response);
        }
        return new ApiResult();
    }
}
