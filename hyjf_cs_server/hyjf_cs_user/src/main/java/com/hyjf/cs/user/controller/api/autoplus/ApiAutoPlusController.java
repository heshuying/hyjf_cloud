/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.autoplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:31
 */

@Api(value = "api端用户授权自动投资自动授权接口")
@RestController
@RequestMapping("/api/user/auto")
public class ApiAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(ApiAutoPlusController.class);

    @Autowired
    AutoPlusService autoPlusService;

    /**
     * 前导发送短信验证码
     * @param autoPlusRequestBean
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
    public ApiResult sendCode(@RequestHeader(value = "userId") Integer userId,@RequestBody AutoPlusRequestBean autoPlusRequestBean) {
        logger.info("api端授权申请发送短信验证码第三方请求参数：" + JSONObject.toJSONString(autoPlusRequestBean));
        ApiResult result = new ApiResult();
        String mobile = autoPlusRequestBean.getMobile();
        String channel = autoPlusRequestBean.getChannel();
        String srvTxCode = autoPlusService.checkApiSmsParam(autoPlusRequestBean);
        // 调用短信发送接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId,mobile,srvTxCode, channel,null);
        } catch (Exception e) {
            logger.error("请求验证码接口发生异常", e);
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        CheckUtil.check(null!=bankBean,MsgEnum.STATUS_CE999999);
        // 短信发送返回结果码
        String retCode = bankBean.getRetCode();
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)
                && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            logger.info("短信验证码发送失败，请稍后再试！");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        if (Validator.isNull(bankBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            logger.info("短信验证码发送失败，请稍后再试！");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :自动投资授权
     * @Param: * @param payRequestBean
     * @Date: 16:44 2018/5/30
     * @Return:
     */
    @ApiOperation(value = "自动投资授权", notes = "自动投资授权")
    @PostMapping(value = "/userAuthInves", produces = "application/json; charset=utf-8")
    public ApiResult<Object> userAuthInves(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ApiResult<Object> result = new ApiResult<Object>();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if (ClientConstants.FAIL.equals(paramMap.get("isSuccess"))) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("callBackForm", paramMap);
            result.setData(resultMap);
        }else {
            Map<String, Object> map = autoPlusService.apiUserAuth(ClientConstants.INVES_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
            result.setData(map);
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户自动债转授权
     * @Param: * @param payRequestBean
     * @Date: 16:45 2018/5/30
     * @Return:
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value = "/userAuthCredit", produces = "application/json; charset=utf-8")
    public ApiResult<Object> userAuthCredit(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ApiResult<Object> result = new ApiResult<Object>();
        Map<String, String> paramMap = autoPlusService.checkParam(payRequestBean);
        if (ClientConstants.FAIL.equals(paramMap.get("isSuccess"))) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("callBackForm", paramMap);
            result.setData(resultMap);
        }else {
            Map<String,Object> map = autoPlusService.apiUserAuth(ClientConstants.CREDIT_AUTO_TYPE, paramMap.get("smsSeq"), payRequestBean);
            result.setData(map);
        }
        return result;
    }

    /**
     * @param bean
     * @Author: zhangqingqing
     * @Desc :自动投资授权同步回调
     * @Param: * @param request
     * @Date: 10:11 2018/5/31
     * @Return:
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
     * @Return:
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
     * 异步回调
     * @param request
     * @param bean
     * @return
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
     * 异步回调
     * @param request
     * @param bean
     * @return
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
