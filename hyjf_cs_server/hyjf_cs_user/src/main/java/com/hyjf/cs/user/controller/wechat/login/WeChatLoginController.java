/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.login;

import com.hyjf.common.enums.MsgEnum;
import com.hyjf.cs.user.vo.LoginRequestVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.controller.BaseUserController;
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
public class WeChatLoginController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatLoginController.class);
    @Autowired
    private LoginService loginService;

    /**
     * @Author: zhangqingqing
     * @Desc :登录接口
     * @Param: * @param request
     * @param user
     * @Date: 16:35 2018/5/30
     * @Return:
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public WeChatResult<WebViewUserVO> login(HttpServletRequest request, @RequestBody LoginRequestVO user) {
        logger.info("login start, loginUserName is :{}", user.getUsername());
        // 现只支持两个参数  1微信  2风车理财
        if (!"1".equals(user.getEnv()) && !"2".equals(user.getEnv())) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }
        WeChatResult<WebViewUserVO> result = new WeChatResult<WebViewUserVO>();
        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(user.getUsername())) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN);
        }
        WebViewUserVO userVO = loginService.login(user.getUsername(), user.getPassword(), GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("weChat端登录成功, userId is :{}", userVO.getUserId());
            result.setData(userVO);
        } else {
            logger.error("weChat端登录失败...");
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
     * @Return: ApiResult
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "logout")
    public WeChatResult<String> loginout(@RequestHeader(value = "token") String token){
        WeChatResult<String> result = new WeChatResult<>();
        // 退出到首页
        result.setData("index");
        try {
            RedisUtils.del(RedisKey.USER_TOKEN_REDIS + token);
        }catch (Exception e){
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("退出失败");
        }
        return result;
    }
}
