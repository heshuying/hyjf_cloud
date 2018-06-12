/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.util.SecretUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version AppUserController, v0.1 2018/6/11 14:51
 */

@Api(value = "app端用户接口")
@RestController
@RequestMapping("/app/appUser")
public class AppSafeController {

    private static final Logger logger = LoggerFactory.getLogger(AppSafeController.class);

    @Autowired
    private SafeService safeService;

    /**
     * 修改登陆密码
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @ResponseBody
    @PostMapping(value = "/updatePasswordAction")
    public JSONObject updatePasswordAction(HttpServletRequest request) {
        logger.info("AppUserController.updatePasswordAction start.....");
        JSONObject ret = new JSONObject();
        ret.put("request", "/updatePasswordAction");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // token
        String token = request.getParameter("token");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 旧密码
        String oldPassword = request.getParameter("oldPassword");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(token) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 取得用户登录信息
        Integer userId = SecretUtil.getUserId(sign);
        String username = SecretUtil.getUserName(sign);
        if (username != null || userId != null) {
            try {
                // 解密
                oldPassword = DES.decodeValue(key, oldPassword);
                newPassword = DES.decodeValue(key, oldPassword);
                ret = safeService.updatePassWd(userId, oldPassword, newPassword);
            } catch (Exception e) {
                ret.put("status", "1");
                ret.put("statusDesc", "修改密码失败");
                return ret;
            }
        } else {
            ret.put("status", "1");
            ret.put("statusDesc", "用户信息不存在");
            return ret;
        }
        return ret;
    }

}
