/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.autoplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.ApiAutoPlusResultBean;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
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

@Api(tags = "api端-用户授权自动投资自动授权接口")
@RestController
@RequestMapping("/hyjf-api/server/autoPlus")
public class ApiAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(ApiAutoPlusController.class);

    /**
     * 请求失败页面路径 /bank/user/trusteePay/error
     */
    public static final String PATH_TRUSTEE_PAY_ERROR = "/bank/user/trusteePay/error";

    @Autowired
    AutoPlusService autoPlusService;

    /**
     * 前导发送短信验证码
     * @param autoPlusRequestBean
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/sendcode.do", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "前导发送短信验证码",notes = "前导发送短信验证码")
    public ApiAutoPlusResultBean sendCode(@RequestHeader(value = "userId") Integer userId,@RequestBody AutoPlusRequestBean autoPlusRequestBean) {
        logger.info("api端授权申请发送短信验证码第三方请求参数：" + JSONObject.toJSONString(autoPlusRequestBean));
        ApiAutoPlusResultBean result = new ApiAutoPlusResultBean();
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
        result.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        result.setStatusDesc("发送短信验证码成功");
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
    @PostMapping(value = "/userAuthInves")
    @ResponseBody
    public ModelAndView userAuthInves(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView(PATH_TRUSTEE_PAY_ERROR);
        Map<String,Object> map  = autoPlusService.checkParam(payRequestBean,modelAndView,BankCallConstant.QUERY_TYPE_1);
        if (null!=map.get("modelAndView")) {
            return modelAndView;
        }else {
            String smsSeq = map.get("smsSeq").toString();
            UserVO user = (UserVO) map.get("user");
            BankCallBean bean = autoPlusService.apiUserAuth(ClientConstants.INVES_AUTO_TYPE,smsSeq ,user, payRequestBean);
            // 插入日志
            autoPlusService.insertUserAuthLog(user, bean, 1, BankCallConstant.QUERY_TYPE_1);
            try {
                modelAndView = BankCallUtils.callApi(bean);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("调用银行接口失败:"+e.getMessage());
                Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999,"系统异常！");
                payRequestBean.doNotify(params);
                autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE999999);
                return modelAndView;
            }
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户自动债转授权
     * @Param: * @param payRequestBean
     * @Date: 16:45 2018/5/30
     * @Return:
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value = "/userCreditAuthInves.do", produces = "application/json; charset=utf-8")
    public ModelAndView userAuthCredit(@RequestBody @Valid AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView(PATH_TRUSTEE_PAY_ERROR);
        Map<String, Object> map = autoPlusService.checkParam(payRequestBean, modelAndView, BankCallConstant.QUERY_TYPE_2);
        if (null!=map.get("modelAndView")) {
            return modelAndView;
        }else {
            String smsSeq = map.get("smsSeq").toString();
            UserVO user = (UserVO) map.get("user");
            BankCallBean bean = autoPlusService.apiUserAuth(ClientConstants.CREDIT_AUTO_TYPE,smsSeq ,user, payRequestBean);
            // 插入日志
            autoPlusService.insertUserAuthLog(user, bean, 1, BankCallConstant.QUERY_TYPE_2);
            try {
                modelAndView = BankCallUtils.callApi(bean);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("调用银行接口失败:"+e.getMessage());
                Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999,"系统异常！");
                payRequestBean.doNotify(params);
                autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE999999);
                return modelAndView;
            }
        }
        return modelAndView;
    }

    /**
     * @param bean
     * @Author: zhangqingqing
     * @Desc :自动投资授权同步回调
     * @Param: * @param request
     * @Date: 10:11 2018/5/31
     * @Return:
     */
    @ApiOperation(value = "自动投资授权同步回调",notes = "自动投资授权同步回调")
    @GetMapping(value = "/userAuthInvesReturn")
    public ModelAndView userAuthInvesReturn(HttpServletRequest request, BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes, ClientConstants.QUERY_TYPE_1);
        if (repwdResult.get("flag").equals("1")){
            modelAndView.addObject("statusDesc", "自动债转授权申请失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));
        }
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
    @ApiOperation(value = "自动债转授权同步回调",notes = "自动债转授权同步回调")
    @GetMapping(value = "/userCreditAuthInvesReturn")
    public ModelAndView userCreditAuthInvesReturn(HttpServletRequest request,BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        AutoPlusRetBean repwdResult = autoPlusService.userAuthCreditReturn(bean, callback, acqRes, ClientConstants.QUERY_TYPE_2);
        if (repwdResult.get("flag").equals("1")){
            modelAndView.addObject("statusDesc", "自动债转授权申请失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));
        }
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
    @ApiOperation(value = "授权自动投资异步回调",notes = "授权自动投资异步回调")
    @PostMapping(value = "/userAuthInvesBgreturn")
    public BankCallResult userAuthInvesBgreturn(HttpServletRequest request,@ModelAttribute BankCallBean bean) {
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
    @ApiOperation(value = "授权自动债转异步回调",notes = "授权自动债转异步回调")
    @PostMapping(value = "/userCreditAuthInvesBgreturn")
    public BankCallResult userCreditAuthInvesBgreturn(HttpServletRequest request,@ModelAttribute BankCallBean bean) {
        String callback = request.getParameter("callback").replace("*-*-*", "#");
        String acqRes = request.getParameter("acqRes");
        BankCallResult result = autoPlusService.userAuthInvesBgreturn(bean, callback, acqRes);
        return result;
    }
}
