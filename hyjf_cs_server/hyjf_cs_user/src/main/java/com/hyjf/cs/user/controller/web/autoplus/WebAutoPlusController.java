/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.autoplus;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.BindCardError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:09
 */
@Api(value = "web端用户自动投标自动债转授权")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebAutoPlusController.class);

    @Autowired
    private AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;


    @RequestMapping(value = "/init")
    public String init(Model model) {

        return "init";
    }

    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{type: string} type=1授权自动投标；type=2授权自动债转", dataType = "Map")
    @PostMapping(value = "/autoPlusSendCode", produces = "application/json; charset=utf-8")
    public ApiResult<Object> autoPlusSendCode(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String,String> param) {
        logger.info("Web端用户修改手机号发送短信验证码, param :{}", param);
        ApiResult<Object> result = new ApiResult<Object>();


        UserVO user = autoPlusService.getUsers(token);
        CheckUtil.check(user!=null,MsgEnum.USER_NOT_LOGIN_ERROR);
        CheckUtil.check(user.getMobile()!=null,MsgEnum.MOBILE_ERROR);
        CheckUtil.check(null!=param && StringUtils.isNotBlank(param.get("type")), MsgEnum.PARAM_ERROR);
        String srvTxCode = "1".equals(param.get("type"))? BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS:BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS;
                // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(user.getUserId(),user.getMobile(),srvTxCode, ClientConstants.CHANNEL_PC,null);
        } catch (Exception e) {
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
            logger.error("请求验证码接口发生异常", e);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
            logger.error("请求验证码接口失败");
        }else {
            result.setResult(bankBean.getSrvAuthCode());
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资
     * @Param: * @param token
     * @param request
     * @Date: 16:43 2018/5/30
     * @Return: ModelAndView
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        String lastSrvAuthCode =request.getParameter("lastSrvAuthCode");
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
     * @Return: ModelAndView
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
     * @Return: Map
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @PostMapping(value = "/userAuthInvesReturn", produces = "application/json; charset=utf-8")
    public Map<String, String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,@RequestBody @Valid BankCallBean bean) {
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
     * @Return: Map
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @PostMapping(value = "/credituserAuthInvesReturn", produces = "application/json; charset=utf-8")
    public Map<String, String> userCreditAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,
                                                         @RequestBody @Valid  BankCallBean bean) {
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
    @PostMapping(value = "/userAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public String userAuthInvesBgreturn(@RequestBody @Valid  BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:43 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/credituserAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public String userCreditAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;

    }
}
