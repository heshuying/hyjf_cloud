/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.user.controller.app.login.UserParameters;
import com.hyjf.cs.user.service.BaseUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version LoginService, v0.1 2018/6/11 15:31
 */
public interface LoginService extends BaseUserService {

    /**
     *
     * @param loginUserName
     *            可以是手机号或者用户名
     * @param loginPassword
     * @param ip
     */
    WebViewUserVO login(String loginUserName, String loginPassword, String ip,String channel);

    void checkForApp(String version,String platform,String netStatus);

    void clearMobileCode(Integer userId, String sign);

    UserParameters getUserParameters(Integer userId, String platform, HttpServletRequest request);

    /**
     * 上传用户头像
     * @param userId
     * @param iconUrl
     */
    void updateUserIconImg(Integer userId, String iconUrl);
}
