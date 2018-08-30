/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.UserWithdrawRequestBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sunpeikai
 * @version: UserWithdrawController, v0.1 2018/8/30 10:26
 */
@Api(value = "api端-用户提现接口",tags = "api端-用户提现接口")
@Controller
@RequestMapping(value = "/server/user/withdraw")
public class ApiUserWithdrawController extends BaseUserController {

    @Autowired
    private UserWithdrawService userWithdrawService;

    /**
     * 用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "外部服务接口用户提现",notes = "外部服务接口用户提现")
    @PostMapping(value = "/withdraw")
    public ModelAndView withdraw(@RequestBody UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request){
        logger.info("外部服务接口用户提现");
        logger.info("用户提现第三方请求参数:" + JSONObject.toJSONString(userWithdrawRequestBean));
        return userWithdrawService.withdraw(userWithdrawRequestBean, request);
    }

    /**
     * 用户提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户提现后处理",notes = "用户提现后处理")
    @RequestMapping(value = "/return")
    public ModelAndView cashReturn(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        logger.info("用户提现后同步处理请求参数:" + JSONObject.toJSONString(bean));
        return userWithdrawService.cashReturn(request, bean);
    }

    /**
     * 用户提现异步回调处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "用户提现异步回调处理",notes = "用户提现异步回调处理")
    @ResponseBody
    @RequestMapping(value = "/callback")
    public BankCallResult withdrawBgReturn(HttpServletRequest request, @ModelAttribute BankCallBean bean) {
        logger.info("用户提现异步回调处理请求参数:" + JSONObject.toJSONString(bean));
        return userWithdrawService.withdrawBgReturn(request, bean);
    }

    /**
     * 获取用户提现记录
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "获取用户提现记录",notes = "获取用户提现记录")
    @ResponseBody
    @RequestMapping(value = "/getUserWithdrawRecord")
    public BaseResultBean getUserWithdrawRecord(@RequestBody UserWithdrawRequestBean userWithdrawRequestBean){
        logger.info("获取用户提现记录请求参数:" + JSONObject.toJSONString(userWithdrawRequestBean));
        return userWithdrawService.getUserWithdrawRecord(userWithdrawRequestBean);
    }
}
