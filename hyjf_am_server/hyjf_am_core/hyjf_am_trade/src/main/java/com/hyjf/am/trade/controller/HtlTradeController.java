/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.HtlProductIntoRecordResponse;
import com.hyjf.am.response.trade.HtlProductRedeemResponse;
import com.hyjf.am.resquest.user.HtlTradeRequest;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: HtlTradeController, v0.1 2018/7/25 15:22
 */
@Api(value = "获取我的账单-汇天利",description = "获取我的账单-汇天利")
@RestController
@RequestMapping(value = "/am-trade/htl")
public class HtlTradeController {

    /**
     * 根据条件获取汇天利购买记录count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/countHtlIntoRecord")
    public HtlProductIntoRecordResponse countHtlIntoRecord(@RequestBody HtlTradeRequest request){
        return null;
    }

    /**
     * 根据条件获取汇天利购买记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getIntoRecordList")
    public HtlProductIntoRecordResponse getIntoRecordList(@RequestBody HtlTradeRequest request){
        return null;
    }
    /**
     * 根据条件获取汇天利赎回记录count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/countProductRedeemRecord")
    public HtlProductRedeemResponse countProductRedeemRecord(@RequestBody HtlTradeRequest request){
        return null;
    }

    /**
     * 根据条件获取汇天利赎回记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getRedeemRecordList")
    public HtlProductRedeemResponse getRedeemRecordList(@RequestBody HtlTradeRequest request){
        return null;
    }
}
