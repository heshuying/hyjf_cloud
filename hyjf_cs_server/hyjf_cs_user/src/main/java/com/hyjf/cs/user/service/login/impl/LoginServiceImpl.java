/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login.impl;

import com.google.common.collect.ImmutableMap;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.service.login.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version LoginServiceImpl, v0.1 2018/6/11 15:32
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AmUserClient amUserClient;
    /**
     *登录
     * @param loginUserName
     *           手机号
     * @param loginPassword
     * @param ip
     */
    @Override
    public UserVO login(String loginUserName, String loginPassword, String ip) {
        if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
            throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
        }
        // 获取密码错误次数
        String errCount = RedisUtils.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
        if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
            throw new ReturnMessageException(LoginError.PWD_ERROR_TOO_MANEY_ERROR);
        }
        return this.doLogin(loginUserName, loginPassword, ip);
    }

    /**
     * 登录处理
     *
     * @param loginUserName
     * @param loginPassword
     * @return
     */
    private UserVO doLogin(String loginUserName, String loginPassword, String ip) {
        UserVO userVO = amUserClient.findUserByUserNameOrMobile(loginUserName);

        if (userVO == null) {
            throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
        }

        int userId = userVO.getUserId();
        String codeSalt = userVO.getSalt();
        String passwordDb = userVO.getPassword();
        // 页面传来的密码
        String password = MD5Utils.MD5(loginPassword + codeSalt);

        if (password.equals(passwordDb)) {
            // 是否禁用
            if (userVO.getStatus() == 1) {
                throw new ReturnMessageException(LoginError.USER_INVALID_ERROR);
            }

            // 更新登录信息
            amUserClient.updateLoginUser(userId, ip);

            // 1. 登录成功将登陆密码错误次数的key删除
            RedisUtils.del(RedisKey.PASSWORD_ERR_COUNT + loginUserName);

            // 2. 缓存
            String token = generatorToken(userVO.getUserId(), userVO.getUsername());
            WebViewUser webViewUser = new WebViewUser();
            BeanUtils.copyProperties(userVO,webViewUser);
            userVO.setToken(token);
            RedisUtils.setObjEx(RedisKey.USER_TOKEN_REDIS + token, webViewUser, 7 * 24 * 60 * 60);

            // 3. todo 登录时自动同步线下充值记录

            return userVO;
        } else {
            // 密码错误，增加错误次数
            RedisUtils.incr(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
            throw new ReturnMessageException(LoginError.USER_LOGIN_ERROR);
        }
    }
    /**
     * 字符串长度检查
     *
     * @param value
     * @param max
     * @return
     */
    private boolean checkMaxLength(String value, int max) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if (value.length() > max) {
            return true;
        }
        return false;
    }

    /**
     * jwt生成token
     *
     * @param userId
     * @param username
     * @return
     */
    private String generatorToken(int userId, String username) {
        Map map = ImmutableMap.of("userId", String.valueOf(userId), "username", username, "ts",
                String.valueOf(Instant.now().getEpochSecond()));
        String token = JwtHelper.genToken(map);
        return token;
    }
}
