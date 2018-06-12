/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user.web.autoplus;

import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:09
 */
@Api(value = "web端用户自动投标自动债转授权")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebAutoPlusController {

    private static final Logger logger = LoggerFactory.getLogger(WebAutoPlusController.class);

    @Autowired
    private AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资
     * @Param: * @param token
     * @param request
     * @Date: 16:43 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = autoPlusService.userCreditAuthInves(token, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_1, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = com.hyjf.pay.lib.bank.util.BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转
     * @Param: * @param token
     * @param request
     * @Date: 16:42 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
    @PostMapping("/creditUserAuthInves")
    public ModelAndView creditUserAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = autoPlusService.userCreditAuthInves(token, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_2, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = com.hyjf.pay.lib.bank.util.BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:42 2018/5/30
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @PostMapping("/userAuthInvesReturn")
    public Map<String, String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, BankCallBean bean) {
        logger.info("userAuthInvesReturn:" + "[投资人自动投标签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = autoPlusService.userAuthReturn(token, bean, ClientConstants.INVES_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:42 2018/5/30
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @PostMapping("/credituserAuthInvesReturn")
    public Map<String, String> userCreditAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,
                                                         @ModelAttribute BankCallBean bean) {
        logger.info("[投资人自动债转签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = autoPlusService.userAuthReturn(token, bean, ClientConstants.CREDIT_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @PostMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:43 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping("/credituserAuthInvesBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;

    }
}
