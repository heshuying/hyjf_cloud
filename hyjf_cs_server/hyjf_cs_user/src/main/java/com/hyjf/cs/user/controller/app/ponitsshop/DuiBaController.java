/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.ponitsshop;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.service.pointsshop.DuiBaService;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjun
 * @version DuiBaController, v0.1 2019/5/29 14:08
 */
@Api(value = "app端-兑吧相关接口", tags = "app端-兑吧相关接口")
@RestController
@RequestMapping("/hyjf-app/pointsshop/duiba")
public class DuiBaController {
    @Autowired
    DuiBaService duiBaService;

    @ApiOperation(value = "获取兑吧跳转url", notes = "获取兑吧跳转url")
    @PostMapping(value = "/geturl")
    public JSONObject getUrl(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request){
        return duiBaService.getUrl(userId, request);
    }

    @ApiOperation(value = "兑吧扣积分接口回调", notes = "兑吧扣积分接口回调")
    @PostMapping(value = "/deductpoints")
    public CreditConsumeResult deductPoints(@RequestBody CreditConsumeParams consumeParams){
        return duiBaService.deductPoints(consumeParams);
    }

}
