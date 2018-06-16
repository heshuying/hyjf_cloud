/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.LoginRequestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.controller.BaseUserController;
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
 * @version LoginController, v0.1 2018/6/11 13:56
 */
@Api(value = "web端用户登录接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebLoginController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebLoginController.class);

    @Autowired
    private LoginService loginService;


    /**
     * @param user
     * @param request
     * @Author: zhangqingqing
     * @Desc :登录
     * @Param: * @param loginUserName
     * @Date: 16:39 2018/5/30
     * @Return:
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> login(@RequestBody LoginRequestVO user,
                                   HttpServletRequest request) {
        logger.info("web端登录接口, user is :{}", JSONObject.toJSONString(user));
        String loginUserName = user.getUsername();
        String loginPassword = user.getPassword();
        ApiResult<UserVO> result = new ApiResult<UserVO>();
        UserVO userVO = loginService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("web端登录成功 userId is :{}", userVO.getUserId());
            result.setResult(userVO);
        } else {
            logger.error("web端登录失败...");
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
     * @Return:
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
