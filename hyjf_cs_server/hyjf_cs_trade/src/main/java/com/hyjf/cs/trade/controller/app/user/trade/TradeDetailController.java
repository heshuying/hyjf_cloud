/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.user.trade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.TradeDetailService;
import com.hyjf.cs.trade.util.ProjectConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jun
 * @version TradeDetailController, v0.1 2018/7/25 14:05
 */
@Api(value = "app端交易详情",description = "app端交易详情")
@RestController
@RequestMapping("/hyjf-app/user/trade")
public class TradeDetailController extends BaseTradeController {

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

}
