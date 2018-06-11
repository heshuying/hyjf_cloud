/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user.api.autoplus;

import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoPlusRetBean;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping(value = "/userAuthInves")
    public ModelAndView userAuthInves(AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if ("false".equals(paramMap.get("isSuccess"))) {
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        BankCallBean bean = autoPlusService.apiUserAuth(ClientConstant.INVES_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
        try {
            modelAndView = BankCallUtils.callApi(bean);
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
    @PostMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if ("false".equals(paramMap.get("isSuccess"))) {
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        BankCallBean bean = autoPlusService.apiUserAuth(ClientConstant.CREDIT_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
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
    @RequestMapping("/userAuthInvesReturn")
    public ModelAndView userAuthInvesReturn(HttpServletRequest request, BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes,ClientConstant.QUERY_TYPE_1);
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
    @RequestMapping("/userCreditAuthInvesReturn")
    public ModelAndView userCreditAuthInvesReturn(HttpServletRequest request, BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes,ClientConstant.QUERY_TYPE_2);
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
    @RequestMapping("/userAuthInvesBgreturn")
    public BankCallResult userAuthInvesBgreturn(HttpServletRequest request, BankCallBean bean) {
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
    @RequestMapping("/userCreditAuthInvesBgreturn")
    public BankCallResult userCreditAuthInvesBgreturn(HttpServletRequest request, BankCallBean bean) {
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        BankCallResult result = autoPlusService.userAuthInvesBgreturn(bean, callback, acqRes);
        return result;
    }
}
