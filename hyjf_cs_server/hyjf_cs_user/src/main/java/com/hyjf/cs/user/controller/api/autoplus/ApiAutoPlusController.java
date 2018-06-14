/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.autoplus;

import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:31
 */

@Api(value = "api端用户授权自动投资自动授权接口")
@RestController
@RequestMapping("/api/user")
public class ApiAutoPlusController {

    private static final Logger logger = LoggerFactory.getLogger(ApiAutoPlusController.class);

    @Autowired
    AutoPlusService autoPlusService;

    /**
     * @Author: zhangqingqing
     * @Desc :自动投资授权
     * @Param: * @param payRequestBean
     * @Date: 16:44 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "自动投资授权", notes = "自动投资授权")
    @PostMapping(value = "/userAuthInves", produces = "application/json; charset=utf-8")
    public ModelAndView userAuthInves(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if (ClientConstants.FAIL.equals(paramMap.get("isSuccess"))) {
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        BankCallBean bean = autoPlusService.apiUserAuth(ClientConstants.INVES_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
        try {
            modelAndView = com.hyjf.pay.lib.bank.util.BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用银行接口失败！" + e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999, "系统异常！");
            payRequestBean.doNotify(params);
            Map<String, String> resultMap = autoPlusService.getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE999999);
            modelAndView.addObject("callBackForm", resultMap);
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户自动债转授权
     * @Param: * @param payRequestBean
     * @Date: 16:45 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value = "/userAuthCredit", produces = "application/json; charset=utf-8")
    public ModelAndView userAuthCredit(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if (ClientConstants.FAIL.equals(paramMap.get("isSuccess"))) {
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        BankCallBean bean = autoPlusService.apiUserAuth(ClientConstants.CREDIT_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用银行接口失败！" + e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999, "系统异常！");
            payRequestBean.doNotify(params);
            Map<String, String> autoPlusRetBean = autoPlusService.getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE999999);
            modelAndView.addObject("callBackForm", autoPlusRetBean);
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * @param bean
     * @Author: zhangqingqing
     * @Desc :自动投资授权同步回调
     * @Param: * @param request
     * @Date: 10:11 2018/5/31
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @PostMapping(value = "/userAuthInvesReturn", produces = "application/json; charset=utf-8")
    public ModelAndView userAuthInvesReturn(HttpServletRequest request,@RequestBody @Valid  BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes, ClientConstants.QUERY_TYPE_1);
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;

    }

    /**
     * @param bean
     * @Author: zhangqingqing
     * @Desc :自动债转授权同步回调
     * @Param: * @param request
     * @Date: 10:11 2018/5/31
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @PostMapping(value = "/userCreditAuthInvesReturn", produces = "application/json; charset=utf-8")
    public ModelAndView userCreditAuthInvesReturn(HttpServletRequest request,@RequestBody @Valid  BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes, ClientConstants.QUERY_TYPE_2);
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;

    }

    /**
     * @Author: zhangqingqing
     * @Desc :异步回调
     * @Param: * @param request
     * @param bean
     * @Date: 10:33 2018/5/31
     * @Return: com.hyjf.pay.lib.bank.bean.BankCallResult
     */
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public BankCallResult userAuthInvesBgreturn(HttpServletRequest request,@RequestBody @Valid  BankCallBean bean) {
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        BankCallResult result = autoPlusService.userAuthInvesBgreturn(bean, callback, acqRes);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :异步回调
     * @Param: * @param request
     * @param bean
     * @Date: 10:33 2018/5/31
     * @Return: com.hyjf.pay.lib.bank.bean.BankCallResult
     */
    @ResponseBody
    @PostMapping(value = "/userCreditAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public BankCallResult userCreditAuthInvesBgreturn(HttpServletRequest request,@RequestBody @Valid  BankCallBean bean) {
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        BankCallResult result = autoPlusService.userAuthInvesBgreturn(bean, callback, acqRes);
        return result;
    }
}
