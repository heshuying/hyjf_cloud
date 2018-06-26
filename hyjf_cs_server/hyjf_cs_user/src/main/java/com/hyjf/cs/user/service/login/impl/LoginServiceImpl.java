/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login.impl;

import com.google.common.collect.ImmutableMap;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.jwt.JwtHelper;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.vo.LoginRequestVO;
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
public class LoginServiceImpl extends BaseUserServiceImpl implements LoginService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     *登录
     * @param loginUserName
     *           手机号
     * @param loginPassword
     * @param ip
     */
    @Override
    public WebViewUserVO login(String loginUserName, String loginPassword, String ip) {
        if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
            CheckUtil.check(false,MsgEnum.ERR_USER_LOGIN);
        }
        // 获取密码错误次数
        String errCount = RedisUtils.get(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
        if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
            CheckUtil.check(false,MsgEnum.ERR_PASSWORD_ERROR_TOO_MANEY);
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
    private WebViewUserVO doLogin(String loginUserName, String loginPassword, String ip) {
        UserVO userVO = amUserClient.findUserByUserNameOrMobile(loginUserName);
        WebViewUserVO webViewUserVO = new WebViewUserVO();
        CheckUtil.check(userVO != null,MsgEnum.ERR_USER_LOGIN);
        int userId = userVO.getUserId();
        String codeSalt = userVO.getSalt();
        String passwordDb = userVO.getPassword();
        // 页面传来的密码
        String password = MD5Utils.MD5(loginPassword + codeSalt);

        if (password.equals(passwordDb)) {
            // 是否禁用
            if (userVO.getStatus() == 1) {
                throw new ReturnMessageException(MsgEnum.ERR_USER_INVALID);
            }
            // 更新登录信息
            amUserClient.updateLoginUser(userId, ip);
            updateUserByUserId(userVO);
            // 1. 登录成功将登陆密码错误次数的key删除
            RedisUtils.del(RedisKey.PASSWORD_ERR_COUNT + loginUserName);

            // 2. 缓存
            String token = generatorToken(userVO.getUserId(), userVO.getUsername());
            BeanUtils.copyProperties(userVO, webViewUserVO);
            webViewUserVO.setToken(token);
            RedisUtils.setObjEx(RedisKey.USER_TOKEN_REDIS + token, webViewUserVO, 7 * 24 * 60 * 60);
            // 3. todo pangchengchao登录时自动同步线下充值记录
        } else {
            // 密码错误，增加错误次数
            RedisUtils.incr(RedisKey.PASSWORD_ERR_COUNT + loginUserName);
            CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
        }
        return webViewUserVO;
    }

    /**
     * 校验app参数
     * @param
     * @return
     */
    @Override
    public void checkForApp(LoginRequestVO loginRequestVO){
        CheckUtil.check(loginRequestVO!=null,MsgEnum.STATUS_CE000001);
        String version = loginRequestVO.getVersion();
        String platform = loginRequestVO.getPlatform();
        String netStatus = loginRequestVO.getNetStatus();
        CheckUtil.check(StringUtils.isNotEmpty(version),MsgEnum.STATUS_CE000014);
        CheckUtil.check(StringUtils.isNotEmpty(platform)&&StringUtils.isNotEmpty(netStatus),MsgEnum.STATUS_CE000001);
        if(version.length()>=5){
            version = version.substring(0, 5);
        }
        CheckUtil.check(version.compareTo("1.4.0")>0,MsgEnum.STATUS_CE000014);

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
