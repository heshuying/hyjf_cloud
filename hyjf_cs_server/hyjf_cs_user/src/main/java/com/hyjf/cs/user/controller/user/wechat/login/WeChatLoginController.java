/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user.wechat.login;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version LoginController, v0.1 2018/6/11 14:33
 */
@Api(value = "weChat端用户登录接口")
@RestController
@RequestMapping("/wechat/user")
public class WeChatLoginController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatLoginController.class);
    @Autowired
    private LoginService loginService;

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
        UserVO userVO = loginService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
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
     * @Desc : 退出登录
     * @Param: * @param token
     * @Date: 16:29 2018/6/5
     * @Return: com.hyjf.cs.user.result.ApiResult<java.lang.String>
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "logout")
    public ApiResult<String> loginout(@RequestHeader(value = "token") String token){
        ApiResult<String> result = new ApiResult<>();
        // 退出到首页
        result.setResult("index");
        try {
            RedisUtils.del(RedisKey.USER_TOKEN_REDIS + token);
        }catch (Exception e){
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("退出失败");
        }
        return result;
    }
}
