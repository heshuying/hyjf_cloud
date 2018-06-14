/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.LoginRequestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
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
 * @version LoginController, v0.1 2018/6/11 14:43
 */

@Api(value = "app端用户登录接口")
@RestController
@RequestMapping("/app/appUser")
public class AppLoginController {

    private static final Logger logger = LoggerFactory.getLogger(AppLoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param user
     * * @param request
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> login(@RequestHeader String key,@RequestBody LoginRequestVO user,
                                   HttpServletRequest request) {
        logger.info("App端登录接口, user is :{}", JSONObject.toJSONString(user));
        ApiResult<UserVO> result = new ApiResult<UserVO>();
        // 账户密码解密
        String loginUserName = DES.decodeValue(key, user.getUsername());
        String loginPassword = DES.decodeValue(key, user.getPassword());

        if (Validator.isNull(loginUserName) || Validator.isNull(loginPassword)||!CommonUtils.isMobile(loginUserName)) {
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
        }
        loginService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
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
