/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.login;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.LoginResultBean;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version LoginController, v0.1 2018/6/11 14:33
 */
@Api(value = "weChat端用户登录接口", tags = "weChat端-用户登录接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/login")
public class WeChatLoginController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatLoginController.class);
    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     *
     * @param request
     * @param userName
     * @param password
     * @param env
     * @return
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/doLogin.do")
    public BaseResultBean login(HttpServletRequest request, @RequestParam String userName, @RequestParam String password,
                                @RequestParam(value = "env", defaultValue = "") String env) {
        LoginResultBean result = new LoginResultBean();
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
        CheckUtil.check(null != userName && null != password, MsgEnum.STATUS_CE000001);
        // 现只支持两个参数  1微信  2风车理财
        if (!"1".equals(env) && !"2".equals(env)) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }
        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(userName)) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN);
        }
        //判断用户输入的密码错误次数---开始
        Map<String, String> errorInfo=loginService.insertErrorPassword(userName,password,BankCallConstant.CHANNEL_WEI);
        if (!errorInfo.isEmpty()){
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(errorInfo.get("info"));
        }
        //判断用户输入的密码错误次数---结束
        WebViewUserVO userVO = loginService.login(userName, password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI);
        if (userVO != null) {
            logger.info("weChat端登录成功, userId is :{}", userVO.getUserId());
            if (StringUtils.isNotBlank(env)) {
                //登录成功之后风车理财的特殊标记，供后续投资使用
                RedisUtils.del("loginFrom" + userVO.getUserId());
                RedisUtils.set("loginFrom" + userVO.getUserId(), env, 1800);
            }
            // 登录完成返回值
            result.setStatus(ResultEnum.SUCCESS.getStatus());
            result.setStatusDesc("登录成功");
            result.setSign(userVO.getToken());
        } else {
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }

    /**
     * 退出操作
     * 请求地址:/wx/login/doLoginOut.do
     * 需要参数: sign
     * @param sign
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "/doLoginOut.do")
    public BaseResultBean doLoginOut( String sign) {
        LoginResultBean result = new LoginResultBean();
        result.setStatus(ResultEnum.SUCCESS.getStatus());
        result.setStatusDesc("退出成功");

        if(StringUtils.isBlank(sign)){
            return result.setEnum(ResultEnum.PARAM);
        }
        AppUserToken token = SecretUtil.getAppUserToken(sign);
        if (token != null && token.getUserId() != null) {
            // 清除sign
            SecretUtil.clearToken(sign);
            RedisUtils.del("loginFrom"+token.getUserId());
        } else {
            result.setEnum(ResultEnum.ERROR_004);
        }
        return result;
    }

}
