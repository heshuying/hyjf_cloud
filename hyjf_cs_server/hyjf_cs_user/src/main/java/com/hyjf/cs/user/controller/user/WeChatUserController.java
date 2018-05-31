/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.AppUserService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version WeChatUserController, v0.1 2018/5/21 09:57
 */
@Api(value = "weChat端用户接口")
@RestController
@RequestMapping("/wechat/user")
public class WeChatUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatUserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    AppUserService appUserService;

    /**
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param:  * @param key
     * @param mobile
     * @param verificationCode
     * @param password
     * @param reffer
     * @param request
     * @param response
     * @Date: 16:34 2018/5/30
     * @Return: com.hyjf.cs.user.result.BaseResultBean
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public BaseResultBean register(@RequestHeader String key, @RequestParam String mobile,
                                   @RequestParam String verificationCode, @RequestParam String password,
                                   @RequestParam(required = false) String reffer, HttpServletRequest request, HttpServletResponse response) {
        logger.info("register start, mobile is :{}", mobile);
        BaseResultBean resultBean = new BaseResultBean();

        String mobilephone = DES.decodeValue(key, mobile);
        String smsCode = DES.decodeValue(key, verificationCode);
        String pwd = DES.decodeValue(key, password);

        reffer = DES.decodeValue(key, reffer);
        if (StringUtils.isNotBlank(reffer)) {
            int count = amUserClient.countUserByRecommendName(reffer);
            if (count == 0) {
                resultBean.setStatus(LoginError.REFFER_INVALID_ERROR.getErrCode());
                resultBean.setStatusDesc(LoginError.REFFER_INVALID_ERROR.getMessage());
                return resultBean;
            }
        }

        RegisterVO registerVO = new RegisterVO();
        registerVO.setMobilephone(mobilephone);
        registerVO.setPassword(pwd);
        registerVO.setReffer(reffer);
        registerVO.setSmsCode(smsCode);
        UserVO userVO = userService.register(registerVO, GetCilentIP.getIpAddr(request));

        if (userVO != null) {
            logger.info("register success, userId is :{}", userVO.getUserId());
        } else {
            logger.error("register failed...");
            resultBean.setStatus("1");
            resultBean.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
        }
        return resultBean;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :登录接口
     * @Param: * @param request
     * @param loginUserName
     * @param loginPassword
     * @param env
     * @Date: 16:35 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<com.hyjf.am.vo.user.UserVO>
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> login(HttpServletRequest request, @RequestParam String loginUserName, @RequestParam String loginPassword,
                                     @RequestParam String env) {
        // 现只支持两个参数  1微信  2风车理财
        if (!"1".equals(env) && !"2".equals(env)) {
            throw new ReturnMessageException(LoginError.ERROR_PARAM);
        }
        logger.info("login start, loginUserName is :{}", loginUserName);
        ApiResult<UserVO> result = new ApiResult<UserVO>();
        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(loginUserName)) {
            throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
        }
        UserVO userVO = userService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("login success, userId is :{}", userVO.getUserId());
            result.setResult(userVO);
        } else {
            logger.error("login failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户自动债转授权
     * @Param: * @param token
     * @param request
     * @Date: 16:36 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @RequestMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request){
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstant.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :自动投资授权接口
     * @Param: * @param token
     * @param request
     * @Date: 16:36 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "自动投资授权接口", notes = "自动投资授权接口")
    @RequestMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request){

        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = userService.userCreditAuthInves(token, ClientConstant.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstant.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转同步回调
     * @Param: * @param token
     * @param bean
     * @param request
     * @Date: 16:37 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<java.lang.String>
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @RequestMapping("/userAuthCreditReturn")
    public ApiResult<String> userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        ApiResult<String> apiResult  = new ApiResult<>();
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        String result = appUserService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,sign,isSuccess);
        apiResult.setResult(result);
        return apiResult;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资同步回调
     * @Param: * @param token
     * @param bean
     * @param request
     * @Date: 16:37 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<java.lang.String>
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @RequestMapping("/userAuthInvesReturn")
    public ApiResult<String> userAuthInvesReturn(@RequestHeader(value = "token") String token,BankCallBean bean, HttpServletRequest request) {
        ApiResult<String> apiResult  = new ApiResult<>();
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        String result = appUserService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,sign,isSuccess);
        apiResult.setResult(result);
        return apiResult;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @ResponseBody
    @RequestMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @RequestMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {

        String result = userService.userBgreturn(bean);
        return result;
    }

}
