/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;

/**
 * 用户服务:BaseService
 *
 * @author liuyang
 * @version BaseService, v0.1 2018/6/27 9:46
 */
public interface BaseService {


    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    User findUserByUserId(int userId);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserInfo findUsersInfo(int userId);
}
