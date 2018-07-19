/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.cs.user.vo.LoginRequestVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
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
@Api(value = "web端用户登录接口", description = "web端用户登录接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebLoginController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebLoginController.class);

    @Autowired
    private LoginService loginService;


    /**
     * 登录
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public WebResult<WebViewUserVO> login(@RequestBody LoginRequestVO user,
                                          HttpServletRequest request) {
        logger.info("web端登录接口, user is :{}", JSONObject.toJSONString(user));
        String loginUserName = user.getUsername();
        String loginPassword = user.getPassword();
        WebResult<WebViewUserVO> result = new WebResult<WebViewUserVO>();
        WebViewUserVO userVO = loginService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_PC);
        if (userVO != null) {
            logger.info("web端登录成功 userId is :{}", userVO.getUserId());

            result.setData(userVO);
        } else {
            logger.error("web端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc : 退出登录
     * @Param: * @param token
     * @Date: 16:29 2018/6/5
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "logout")
    public WebResult<Object> loginout(@RequestHeader(value = "token") String token){
        WebResult<Object> result = new WebResult<>();
        JSONObject ret = new JSONObject();
        ret.put("request", "/user/logout");
        // 退出到首页
        result.setData("index");
        try {
            RedisUtils.del(RedisKey.USER_TOKEN_REDIS + token);
        }catch (Exception e){
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("退出失败");
        }
        result.setData(ret);
        return result;
    }

}
