/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhangqq
 * @version WeChatUserController, v0.1 2018/5/21 09:57
 */

@RestController
@RequestMapping("/wechat/user")
public class WeChatUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatUserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 注册
     * @param key
     * @param mobile
     * @param verificationCode
     * @param password
     * @param reffer
     * @param request
     * @param response
     * @return
     */
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
     * 登录接口
     * @param request
     * @param loginUserName
     * @param loginPassword
     * @param env
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8")
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
     * 用户自动债转授权
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response){
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
     * 自动投资授权接口
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response){
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
     * 用户授权自动债转同步回调
     * @param token
     * @param bean
     * @param request
     * @return
     */
    @RequestMapping("/userAuthCreditReturn")
    public Map<String,BaseMapBean> userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String,BaseMapBean> result = userService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,sign,isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资同步回调
     * @param token
     * @param bean
     * @param request
     * @return
     */
    @RequestMapping("/userAuthInvesReturn")
    public Map<String,BaseMapBean> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,BankCallBean bean, HttpServletRequest request) {
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String,BaseMapBean> result = userService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,sign,isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @RequestMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

}
