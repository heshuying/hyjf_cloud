/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.user.trade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.bean.app.AppTradeDetailBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.trade.TradeDetailService;
import com.hyjf.cs.trade.util.ProjectConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jun
 * @version AppTradeDetailController, v0.1 2018/7/25 14:05
 */
@Api(value = "app端-交易明细",tags = "app端-交易明细")
@RestController
@RequestMapping("/hyjf-app/user/trade")
public class  AppTradeDetailController extends BaseTradeController {

    @Autowired
    private TradeDetailService tradeDetailService;


    @ApiOperation(value = "获取交易类型", notes = "获取交易类型")
    @PostMapping("/getTradeTypes")
    public JSONObject searchTradeTypes(HttpServletRequest request, HttpServletResponse response) {
        JSONObject info = new JSONObject();
        List<AppAccountTradeListCustomizeVO> tradeTypes = tradeDetailService.searchAppTradeTypes();
        info.put("tradeTypes", tradeTypes);
        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        info.put(CustomConstants.APP_REQUEST, ProjectConstant.REQUEST_HOME + ProjectConstant.USER_TRADE_REQUEST_MAPPING + ProjectConstant.USER_TRADE_TYPES_ACTION);
        return info;

    }
    /**
     * 用户收支明细
     *
     * @param trade
     * @return
     */
    @ApiOperation(value = "用户收支明细", notes = "用户收支明细")
    @ResponseBody
    @PostMapping(value = "/getTradeList",  produces = "application/json; charset=utf-8")
    public AppTradeDetailBean searchTradeDetailList(@RequestHeader(value = "userId" , required = false )Integer userId, AppTradeDetailBeanRequest trade) {

        trade.setUserId(userId);
        AppTradeDetailBean appTradeDetailBean=tradeDetailService.createTradeDetailListPage(trade);
        appTradeDetailBean.setStatus(CustomConstants.APP_STATUS_SUCCESS);
        appTradeDetailBean.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
        appTradeDetailBean.setRequest("/hyjf-app/user/trade/getTradeList");
        return appTradeDetailBean;
    }



}
