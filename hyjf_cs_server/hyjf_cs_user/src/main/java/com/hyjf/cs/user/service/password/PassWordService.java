/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.Map;

/**
 * @author wangc
 * 密码设置相关
 */
public interface PassWordService extends BaseUserService {

    /**
     * 修改登陆密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    WebResult<String> updatePassWd(Integer userId, String oldPW, String newPW, String newPW2);

    /**
     *
     * @param userId
     */
    void updateUserIsSetPassword(Integer userId);

    Map<String,Object> setPassword(UserVO user);

    Map<String,Object> resetPassword(UserVO user);
}
