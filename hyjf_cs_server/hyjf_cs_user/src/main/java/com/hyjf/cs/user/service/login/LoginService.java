/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;

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
    UserVO login(String loginUserName, String loginPassword, String ip);
}
