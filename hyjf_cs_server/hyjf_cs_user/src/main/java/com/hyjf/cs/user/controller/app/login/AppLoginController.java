/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.login;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.SecretUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    SystemConfig systemConfig;
    /**
     * 登录
     *
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login")
    public JSONObject loginInAction(@RequestHeader(value = "version") String version,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response){
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/login");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 用户名
        String username = request.getParameter("username");
        // 密码
        String password = request.getParameter("password");
        // 唯一标识
        String sign = request.getParameter("sign");
        loginService.checkForApp(version,platform,netStatus);
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "709");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
        try {
            // 解密
            username = DES.decodeValue(key, username);
            password = DES.decodeValue(key, password);
            if (Validator.isNull(username)) {
                ret.put("status", "1");
                ret.put("statusDesc", "用户名不能为空");
                return ret;
            }
            if (Validator.isNull(password)) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码不能为空");
                return ret;
            }
            // 执行登录(登录时间，登录ip)
            WebViewUserVO webViewUserVO = loginService.login(username, password, GetCilentIP.getIpAddr(request));
            if (webViewUserVO != null) {
                logger.info("app端登录成功 userId is :{}", webViewUserVO.getUserId());
                ret.put("status", "0");
                ret.put("statusDesc", "登录成功");
                ret.put("token", webViewUserVO.getToken());
                ret.put("sign", sign);

            } else {
                logger.error("app端登录失败...");
                ret.put("status", "1");
                ret.put("statusDesc", "app端登录失败");
            }
        }catch (Exception e){
            logger.error("app端登录失败...");
            ret.put("status", "1");
            ret.put("statusDesc", e.getMessage());
        }
        return ret;
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "/logout")
    public JSONObject loginOutAction(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "version") String version,@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/logout");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(token) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
        try {
            if (userId != null) {
                clearMobileCode(userId,sign);

                // 移除sign
                if(version.substring(0,5).equals(systemConfig.getNewVersion()) && "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604".equals(sign)){
                    logger.info("sign不做移除");
                }else{
                    SecretUtil.clearToken(sign);
                }
                ret.put("status", "0");
                ret.put("statusDesc", "退出登录成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "退出登录发生错误");
        }
        return ret;
    }

    private void clearMobileCode(Integer userId, String sign) {
        loginService.clearMobileCode(userId,sign);

    }

}
