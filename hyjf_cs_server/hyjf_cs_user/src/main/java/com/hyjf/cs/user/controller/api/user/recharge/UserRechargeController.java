/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.recharge;

import com.alibaba.fastjson.JSON;
import com.hyjf.cs.user.bean.ApiUserRechargeRequestBean;
import com.hyjf.cs.user.bean.ApiUserRechargeResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.recharge.ApiRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sunpeikai
 * @version: UserRechargeController, v0.1 2018/8/28 10:23
 */
@Api(value = "api端-用户充值",tags = "api端-用户充值")
@RestController
@RequestMapping(value = "/hyjf-api/server/user/recharge")
public class UserRechargeController extends BaseUserController {

    @Autowired
    private ApiRechargeService apiRechargeService;

    /**
     * 短信充值发送短信验证码
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "短信充值发送短信验证码",notes = "短信充值发送短信验证码")
    @PostMapping(value = "/sendSms")
    public ApiUserRechargeResultBean sendSms(@RequestBody ApiUserRechargeRequestBean requestBean){
        logger.info("",JSON.toJSONString(requestBean));
        ApiUserRechargeResultBean bean = apiRechargeService.sendSms(requestBean);
        return bean;
    }

    /**
     * 短信充值
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "短信充值",notes = "短信充值")
    @PostMapping(value = "/recharge")
    public ApiUserRechargeResultBean recharge(HttpServletRequest request,@RequestBody ApiUserRechargeRequestBean requestBean){
        logger.info("",JSON.toJSONString(requestBean));
        ApiUserRechargeResultBean bean = apiRechargeService.recharge(request,requestBean);
        return bean;
    }
}
