/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.vo.LoginRequestVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version LoginController, v0.1 2018/6/11 14:43
 */

@Api(value = "app端用户登录接口")
@RestController
@RequestMapping("/app/appUser")
public class AppLoginController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppLoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param
     * * @param request
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public AppResult< Map<String,String>> login(@RequestHeader String key, @RequestBody LoginRequestVO loginRequestVO,
                                          HttpServletRequest request) {
        logger.info("App端登录接口, user is :{}", JSONObject.toJSONString(loginRequestVO));
        //检查参数正确性
        loginService.checkForApp(loginRequestVO);
        AppResult< Map<String,String>> result = new AppResult< Map<String,String>>();
        Map<String,String> map = new HashMap<>();
        // 账户密码解密
        String loginUserName = DES.decodeValue(key, loginRequestVO.getUsername());
        String loginPassword = DES.decodeValue(key, loginRequestVO.getPassword());
        CheckUtil.check(StringUtils.isNotEmpty(loginUserName) && StringUtils.isNotEmpty(loginPassword)&&CommonUtils.isMobile(loginUserName),MsgEnum.ERR_USER_LOGIN);
        WebViewUserVO webViewUserVO = loginService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
        if (webViewUserVO != null) {
            logger.info("app端登录成功 userId is :{}", webViewUserVO.getUserId());
            map.put("token",webViewUserVO.getToken());
            result.setData(map);
        } else {
            logger.error("app端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMsg());
        }
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc : 退出登录
     * @Param: * @param token
     * @Date: 16:29 2018/6/5
     * @Return
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "logout")
    public AppResult<String> loginout(@RequestHeader(value = "token") String token){
        AppResult<String> result = new AppResult<>();
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
