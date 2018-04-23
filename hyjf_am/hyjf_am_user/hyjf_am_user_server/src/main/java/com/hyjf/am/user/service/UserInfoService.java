package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.UsersInfo;

/**
 * @author xiasq
 * @version UserInfoService, v0.1 2018/4/23 9:56
 */
public interface UserInfoService {
    UsersInfo findUserInfoById(int userId);
}
